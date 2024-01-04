package dacd.lopez.control;

import dacd.lopez.model.Booking;
import dacd.lopez.model.Hotel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class OpenHotelInfoProvider implements HotelInfoProvider {

    @Override
    public Booking getHotel(Hotel hotel, Booking booking) {
        Instant now = Instant.now().plusSeconds(24 * 60 * 60);
        Instant lastDay = now.plusSeconds(3 * 24 * 60 * 60);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String chkInDate = LocalDateTime.ofInstant(now, ZoneOffset.UTC).format(formatter);
        String chkOutDate = LocalDateTime.ofInstant(lastDay, ZoneOffset.UTC).format(formatter);

        String apiUrl = "https://data.xotelo.com/api/rates?hotel_key=" + hotel.getHotel_key() +
                "&chk_in=" + chkInDate + "&chk_out=" + chkOutDate;

        try {
            Connection.Response response = Jsoup.connect(apiUrl)
                    .ignoreContentType(true)
                    .execute();

            Document document = response.parse();
            JSONObject jsonResponse = new JSONObject(document.body().text());

            String chkIn = jsonResponse.getJSONObject("result").getString("chk_in");
            String chkOut = jsonResponse.getJSONObject("result").getString("chk_out");
            JSONArray ratesArray = jsonResponse.getJSONObject("result").getJSONArray("rates");

            hotel.setHotel_name(hotel.getHotel_name());
            hotel.setLocation(hotel.getLocation());

            booking.setCheck_in(chkIn);
            booking.setCheck_out(chkOut);
            booking.setRates(ratesArray);
            System.out.println("Processing...");

            return booking;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Booking getHotelDetailsForHotel(Hotel hotel) {
        Booking booking = new Booking("default_check_in", "default_check_out", new JSONArray(), hotel);

        hotel.setHotel_name(hotel.getHotel_name());
        hotel.setLocation(hotel.getLocation());

        booking.setRates(new JSONArray());

        try {
            return getHotel(hotel, booking);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
