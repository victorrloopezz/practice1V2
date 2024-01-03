package dacd.lopez.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;

public class Booking {
     private String check_in;
     private String check_out;
     private JSONArray rates;
     private Hotel hotel;
     private final String ss;
     private final Instant ts;

     public Booking(String check_in, String check_out, JSONArray rates, Hotel hotel) {
          this.check_in = check_in;
          this.check_out = check_out;
          this.rates = rates;
          this.ss = "hotel-price-provider";
          this.ts = Instant.now();
          this.hotel = hotel;
     }

     public String getCheck_in() {
          return check_in;
     }

     public String getCheck_out() {
          return check_out;
     }

     public void setCheck_in(String check_in) {
          this.check_in = check_in;
     }

     public void setCheck_out(String check_out) {
          this.check_out = check_out;
     }

     public void setRates(JSONArray rates) {
          this.rates = rates;
     }

     public JSONArray getRates() {
          return rates;
     }

     public Hotel getHotel() {
          return hotel;
     }

     public JSONObject toJSON() {
          JSONObject jsonHotel = new JSONObject();
          jsonHotel.put("chk_in", getCheck_in());
          jsonHotel.put("chk_out", getCheck_out());
          jsonHotel.put("location", hotel.getLocation());
          jsonHotel.put("hotel_name", hotel.getHotel_name());
          jsonHotel.put("rates", rates);
          jsonHotel.put("ss", ss);
          jsonHotel.put("ts", ts.toString());
          return jsonHotel;
     }
}