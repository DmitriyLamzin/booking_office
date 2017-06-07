package com.github.dmitriylamzin.service;

import com.github.dmitriylamzin.domain.Seat;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.AccessTimeout;
import javax.ejb.Local;
import javax.ejb.Lock;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;

/**
 * Created by Dmitriy_Lamzin on 5/30/2017.
 */
@Singleton
@Startup
@AccessTimeout(value = 5, unit = TimeUnit.MINUTES)
@Remote(TheatreBoxRemote.class)
@Local(TheatreBoxLocal.class)
public class TheatreBox implements TheatreBoxRemote, TheatreBoxLocal {

    private static final Logger logger = Logger.getLogger(TheatreBox.class);

    @Inject
    private Event<Seat> seatEvent;

    private Map<Integer, Seat> seats;

    @Override
    @PostConstruct
    public void setupTheatre() {
        seats = new HashMap<>();
        int id = 0;
        for (int i = 0; i < 5; i++) {
            addSeat(new Seat(++id, "Stalls", 40));
            addSeat(new Seat(++id, "Circle", 20));
            addSeat(new Seat(++id, "Balcony", 10));
        }
        logger.info("Seat Map constructed.");
    }
    private void addSeat(Seat seat) {
        seats.put(seat.getId(), seat);
    }
    @Override
    @Lock(READ)
    public ArrayList<Seat> getSeats() {
        return new ArrayList<>(seats.values());
    }
    @Override
    @Lock(READ)
    public int getSeatPrice(int seatId) throws NoSuchSeatException {
        return getSeat(seatId).getPrice();
    }
    @Override
    @Lock(WRITE)
    public void buyTicket(int seatId) throws SeatBookedException,
            NoSuchSeatException {
        final Seat seat = getSeat(seatId);
        if (seat.isBooked()) {
            throw new SeatBookedException("Seat " + seatId + " already booked!");
        }
        addSeat(seat.getBookedSeat());
        seatEvent.fire(seat);
    }
    @Lock(READ)
    private Seat getSeat(int seatId) throws NoSuchSeatException {
        final Seat seat = seats.get(seatId);
        if (seat == null) {
            throw new NoSuchSeatException("Seat " + seatId + " does not exist!");
        }
        return seat;
    }
}
