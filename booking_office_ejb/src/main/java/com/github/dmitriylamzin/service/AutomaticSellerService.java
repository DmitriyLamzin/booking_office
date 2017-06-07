package com.github.dmitriylamzin.service;

import com.github.dmitriylamzin.domain.Seat;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by Dmitriy_Lamzin on 6/2/2017.
 */
@Stateless
public class AutomaticSellerService {
    private static final Logger logger =
            Logger.getLogger(AutomaticSellerService.class);
    @Inject
    private TheatreBoxLocal theatreBox;

    @Resource
    private TimerService timerService;

    @Schedule(hour = "*", minute = "*/5", persistent = false)
    public void automaticCustomer() throws NoSuchSeatException {
        final Optional<Seat> seatOptional = findFreeSeat();
        if (!seatOptional.isPresent()) {
            cancelTimers();
            logger.info("Scheduler gone!");
            return; // No more seats
        }
        final Seat seat = seatOptional.get();
        try {
            theatreBox.buyTicket(seat.getId());
        } catch (SeatBookedException e) {
// do nothing, user booked this seat in the meantime
        }
        logger.info("Somebody just booked seat number " + seat.getId());
    }
    private Optional<Seat> findFreeSeat() {
        final Collection<Seat> list = theatreBox.getSeats();
        return list.stream()
                .filter(seat -> !seat.isBooked())
                .findFirst();
    }
    private void cancelTimers() {
        for (Timer timer : timerService.getTimers()) {
            timer.cancel();
        }
    }
}
