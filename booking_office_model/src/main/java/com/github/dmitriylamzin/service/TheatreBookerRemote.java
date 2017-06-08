package com.github.dmitriylamzin.service;

import java.io.Serializable;
import java.util.concurrent.Future;

/**
 * Created by Dmitriy_Lamzin on 5/30/2017.
 */
public interface TheatreBookerRemote extends Serializable {
    int getAccountBalance();
    String bookSeat(int seatId) throws SeatBookedException,
            NotEnoughMoneyException, NoSuchSeatException;

    Future<String> bookSeatAsync(int seatId);
    int getMoney();

}
