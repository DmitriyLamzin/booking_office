package com.github.dmitriylamzin.service;

import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.concurrent.Future;

/**
 * Created by Dmitriy_Lamzin on 5/30/2017.
 */
@Stateful
@Remote (TheatreBookerRemote.class)
@SessionScoped
@Named
public class TheatreBooker implements TheatreBookerRemote {
    @Inject
    private Logger logger;
    @Inject
    private TheatreBoxRemote theatreBox;

    private int money;

    @PostConstruct
    public void createCustomer() {
        this.money = 100;
    }
    @Override
    public int getAccountBalance() {
        return money;
    }
    @Override
    public String bookSeat(int seatId) throws SeatBookedException,
            NotEnoughMoneyException, NoSuchSeatException {
        final int seatPrice = theatreBox.getSeatPrice(seatId);
        if (seatPrice > money) {
            throw new NotEnoughMoneyException("You don’t have enough money to buy this " + seatId + " seat!");
        }
        theatreBox.buyTicket(seatId);
        money = money - seatPrice;
        logger.infov("Seat {0} booked.", seatId);
        return "Seat booked.";
    }

    @Asynchronous
    @Override
    public Future<String> bookSeatAsync(int seatId) {
        try {
            Thread.sleep(10000);
            bookSeat(seatId);
            return new AsyncResult<>("Booked seat:" + seatId + ". Money left: " + money);
        } catch (NoSuchSeatException | SeatBookedException |
                NotEnoughMoneyException | InterruptedException e) {
            return new AsyncResult<>(e.getMessage());
        }
    }

    public int getMoney() {
        return money;
    }
}
