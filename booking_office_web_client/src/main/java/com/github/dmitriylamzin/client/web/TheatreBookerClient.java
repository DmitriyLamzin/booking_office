package com.github.dmitriylamzin.client.web;

import com.github.dmitriylamzin.client.service.MyQualifier;
import com.github.dmitriylamzin.service.NoSuchSeatException;
import com.github.dmitriylamzin.service.NotEnoughMoneyException;
import com.github.dmitriylamzin.service.SeatBookedException;
import com.github.dmitriylamzin.service.TheatreBookerRemote;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by Dmitriy_Lamzin on 6/7/2017.
 */
@SessionScoped
@Named
public class TheatreBookerClient implements Serializable {

    @Inject
    private TheatreBookerRemote theatreBookerRemote;

    public int getAccountBalance() {
        return theatreBookerRemote.getAccountBalance();
    }

    public String bookSeat(int seatId) throws SeatBookedException, NotEnoughMoneyException, NoSuchSeatException {
        return theatreBookerRemote.bookSeat(seatId);
    }

    public int getMoney() {
        return theatreBookerRemote.getMoney();
    }



}
