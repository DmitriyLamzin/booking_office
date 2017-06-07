package com.github.dmitriylamzin.service;

import com.github.dmitriylamzin.domain.Seat;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

/**
 * Created by Dmitriy_Lamzin on 5/30/2017.
 */
@Stateless
@Remote (TheatreInfoRemote.class)
@Local (TheatreInfoLocal.class)
public class TheatreInfoService implements TheatreInfoRemote, TheatreInfoLocal {
    @Inject
    private TheatreBoxRemote info;

     @Override
    public String printSeatList() {
        final Collection<Seat> seats = info.getSeats();
        final StringBuilder sb = new StringBuilder();
        for (Seat seat : seats) {
            sb.append(seat.toString());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

}
