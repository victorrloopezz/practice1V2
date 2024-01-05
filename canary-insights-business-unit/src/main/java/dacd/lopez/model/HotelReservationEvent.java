package dacd.lopez.model;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class HotelReservationEvent {
    public static class Rate {
        private String code;
        private int rate;
        private String name;
        private int tax;

        public String getCode() {
            return code;
        }

        public double getRate() {
            return rate;
        }

        public String getName() {
            return name;
        }

        public int getTax() {
            return tax;
        }
    }

    private String check_in;
    private String check_out;

    public String getCheck_in() {
        return check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    @SerializedName("rates")
    public RatesContainer ratesContainer;

    public static class RatesContainer {
        public List<Rate> myArrayList;
    }

    public static class Hotel {
        private String location;
        private String hotel_name;
        private String hotel_key;

        public String getLocation() {
            return location;
        }

        public String getHotel_name() {
            return hotel_name;
        }

        public String getHotel_key() {
            return hotel_key;
        }
    }

    private Hotel hotel;

    public Hotel getHotel() {
        return hotel;
    }

    public HotelReservationEvent() {
        Rate rate = new Rate();
        rate.rate = Integer.MAX_VALUE;
        rate.tax = Integer.MAX_VALUE;

        RatesContainer ratesContainer = new RatesContainer();
        ratesContainer.myArrayList = Collections.singletonList(rate);

        this.ratesContainer = ratesContainer;
    }
}
