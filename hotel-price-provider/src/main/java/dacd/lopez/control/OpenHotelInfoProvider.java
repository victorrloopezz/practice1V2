package dacd.lopez.control;

import dacd.lopez.model.Booking;
import dacd.lopez.model.Hotel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OpenHotelInfoProvider implements HotelInfoProvider {

    @Override
    public Booking getHotel(Hotel hotel, Booking booking) {
        Instant now = Instant.now();
        Instant fiveDaysLater = now.plusSeconds(5 * 24 * 60 * 60);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String chkInDate = LocalDateTime.ofInstant(now, ZoneOffset.UTC).format(formatter);
        String chkOutDate = LocalDateTime.ofInstant(fiveDaysLater, ZoneOffset.UTC).format(formatter);

        String apiUrl = "https://data.xotelo.com/api/rates?hotel_key=" + hotel.getHotel_key() +
                "&chk_in=" + chkInDate + "&chk_out=" + chkOutDate;

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonResponse = new JSONObject(response.body());

            String chkIn = jsonResponse.getJSONObject("result").getString("chk_in");
            String chkOut = jsonResponse.getJSONObject("result").getString("chk_out");
            JSONArray ratesArray = jsonResponse.getJSONObject("result").getJSONArray("rates");

            hotel.setHotel_name(hotel.getHotel_name());
            hotel.setLocation(hotel.getLocation());

            booking.setCheck_in(chkIn);
            booking.setCheck_out(chkOut);
            booking.setRates(ratesArray);

            return booking;
        } catch (Exception e) {
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

    public static void main(String[] args) {
        HotelController hotelController = new HotelController();

        List<Booking> bookingList = new ArrayList<>();

        for (Hotel hotel : hotelController.createHotelList()) {
            Booking booking = new OpenHotelInfoProvider().getHotelDetailsForHotel(hotel);
            if (booking != null) {
                bookingList.add(booking);
            }
        }

        for (Booking booking : bookingList) {
            System.out.println("Booking details: " + booking.toJSON().toString());
        }
    }
}
