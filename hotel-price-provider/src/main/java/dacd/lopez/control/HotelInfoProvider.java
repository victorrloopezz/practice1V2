package dacd.lopez.control;

import dacd.lopez.model.Booking;
import dacd.lopez.model.Hotel;

import java.time.Instant;

public interface HotelInfoProvider {
    Booking getHotel(Hotel hotel, Booking booking);
}
