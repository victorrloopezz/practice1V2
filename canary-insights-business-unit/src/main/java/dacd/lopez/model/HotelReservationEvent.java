package dacd.lopez.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HotelReservationEvent {
    public static class Rate {
        public String code;
        public int rate;
        public String name;
        public int tax;
    }

    public String check_in;
    public String check_out;

    // Utiliza RatesContainer para manejar la variabilidad de rates
    @SerializedName("rates")
    public RatesContainer ratesContainer;

    public static class RatesContainer {
        public List<Rate> myArrayList;
    }

    public static class Hotel {
        public String location;
        public String hotel_name;
        public String hotel_key;
    }

    public Hotel hotel;

    public String ss;
    public String ts;
}
