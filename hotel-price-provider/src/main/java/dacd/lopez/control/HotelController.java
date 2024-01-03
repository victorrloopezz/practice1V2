package dacd.lopez.control;

import dacd.lopez.model.Booking;
import dacd.lopez.model.Hotel;

import java.util.ArrayList;
import java.util.List;

public class HotelController {
    private final JmsHotelInfoSender jmsSender;

    public HotelController(JmsHotelInfoSender jmsSender) {
        this.jmsSender = jmsSender;
    }
    public void execute() {
        List<Booking> bookingList = new ArrayList<>();

        for (Hotel hotel : createHotelList()) {
            Booking booking = new OpenHotelInfoProvider().getHotelDetailsForHotel(hotel);
            if (booking != null) {
                bookingList.add(booking);
            }
        }

        for (Booking booking : bookingList) {
            System.out.println("Booking details: " + booking.toJSON().toString());
            jmsSender.send(booking);
        }
    }

    private List<Hotel> createHotelList() {
        return List.of(
                new Hotel("Gran Canaria", "BlueBay Beach-Club", "g562818-d677815"),
                new Hotel("Gran Canaria", "Lopesan Baobab Resort", "g2089121-d1488268"),
                new Hotel("Gran Canaria", "Abora Buenaventura", "g562819-d289606"),
                new Hotel("Tenerife", "Jardin Tropical","g662606-d248458"),
                new Hotel("Tenerife", "Hotel Vulcano", "g562820-d248468"),
                new Hotel("Tenerife", "Hotel Botanico","g187481-d252888"),
                new Hotel("El Hierro", "Parador El Hierro", "g187474-d277394"),
                new Hotel("El Hierro", "Punta Grande", "g2139290-d627753"),
                new Hotel("El Hierro", "Balneario Pozo de la Salud", "g1189149-d1193818"),
                new Hotel("Lanzarote", "Hotel Lancelot", "g187478-d273097"),
                new Hotel("Lanzarote", "Relaxia Lanzasur", "g652121-d292136"),
                new Hotel("Lanzarote", "Hotel Beatriz Costa Spa", "g659633-d291300"),
                new Hotel("La Gomera", "Hotel Torre del Conde", "g187470-d566709"),
                new Hotel("La Gomera", "Hotel Villa Gomera", "g187470-d614341"),
                new Hotel("La Gomera", "Hotel Playa Calera", "g21309597-d1379967"),
                new Hotel("Fuerteventura", "Barcelo Fuerteventura Mar","g658907-d255145"),
                new Hotel("Fuerteventura", "Fuerteventura Princes", "g673234-d500267"),
                new Hotel("Fuerteventura", "Alua Suites", "g580322-d573425"),
                new Hotel("La Palma",  "H10 Taburiente Playa", "g659966-d289252"),
                new Hotel("La Palma", "Parador de LaPalma", "g642213-d482745"),
                new Hotel("La Palma", "Hotel Las Olas", "g642213-d488944"),
                new Hotel("La Graciosa", "La Pardela", "g3360203-d25244761"),
                new Hotel("La Graciosa", "Graciosa Mar", "g1190272-d945848"),
                new Hotel("La Graciosa", "Apartments La Graciosa", "g3360203-d3411835"));
    }

}
