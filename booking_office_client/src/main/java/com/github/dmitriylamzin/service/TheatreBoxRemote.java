package com.github.dmitriylamzin.service;

import com.github.dmitriylamzin.domain.Seat;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;

/**
 * Created by Dmitriy_Lamzin on 6/7/2017.
 */
public interface TheatreBoxRemote extends Serializable {
    @PostConstruct
    void setupTheatre();

    @Lock(LockType.READ)
    ArrayList<Seat> getSeats();

    @Lock(LockType.READ)
    int getSeatPrice(int seatId) throws NoSuchSeatException;

    @Lock(LockType.WRITE)
    void buyTicket(int seatId) throws SeatBookedException,
            NoSuchSeatException;
}
