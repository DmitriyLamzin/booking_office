/**
 * Created by Dmitriy_Lamzin on 5/30/2017.
 */
public interface TheatreBookerRemote {
    int getAccountBalance();
    String bookSeat(int seatId) throws SeatBookedException,
            NotEnoughMoneyException, NoSuchSeatException;
}
