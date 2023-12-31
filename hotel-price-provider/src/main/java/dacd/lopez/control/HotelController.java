package dacd.lopez.control;

import dacd.lopez.model.Booking;
import dacd.lopez.model.Hotel;

import java.util.ArrayList;
import java.util.List;

public class HotelController {
    List<Hotel> createHotelList() {
        return List.of(
                new Hotel("Gran Canaria", "BlueBayBeachClub", "g562818-d677815"),
                new Hotel("Gran Canaria", "LopesanBaobabResort", "g2089121-d1488268"),
                new Hotel("Gran Canaria", "AboraBuenaventura", "g562819-d289606"),
                new Hotel("Tenerife", "JardinTropical","g662606-d248458"),
                new Hotel("Tenerife", "HotelVulcano", "g562820-d248468"),
                new Hotel("Tenerife", "HotelBotanico","g187481-d252888"),
                new Hotel("El Hierro", "ParadorElHierro", "g187474-d277394"),
                new Hotel("El Hierro", "PuntaGrande", "g2139290-d627753"),
                new Hotel("El Hierro", "BalnearioPozoDeLaSalud", "g1189149-d1193818"),
                new Hotel("Lanzarote", "HotelLancelot", "g187478-d273097"),
                new Hotel("Lanzarote", "RelaxiaLanzasur", "g652121-d292136"),
                new Hotel("Lanzarote", "HotelBeatrizCostaSpa", "g659633-d291300"),
                new Hotel("La Gomera", "HotelTorredelConde", "g187470-d566709"),
                new Hotel("La Gomera", "HotelVillaGomera", "g187470-d614341"),
                new Hotel("La Gomera", "HotelPlayaCalera", "g21309597-d1379967"),
                new Hotel("Fuerteventura", "BarceloFuerteventuraMar","g658907-d255145"),
                new Hotel("Fuerteventura", "FuerteventuraPrinces", "g673234-d500267"),
                new Hotel("Fuerteventura", "AluaSuites", "g580322-d573425"),
                new Hotel("La Palma",  "H10TaburientePlaya", "g659966-d289252"),
                new Hotel("La Palma", "ParadorDeLaPalma", "g642213-d482745"),
                new Hotel("La Palma", "HotelLasOlas", "g642213-d488944"),
                new Hotel("La Graciosa", "LaPardela", "g3360203-d25244761"),
                new Hotel("La Graciosa", "GraciosaMar", "g1190272-d945848"),
                new Hotel("La Graciosa", "ApartmentsLaGraciosa", "g3360203-d3411835"));
    }
    public List<Booking> getHotelDetailsList() {
        OpenHotelInfoProvider provider = new OpenHotelInfoProvider();
        List<Hotel> hotels = createHotelList();
        List<Booking> bookingList = new ArrayList<>();

        for (Hotel hotel : hotels) {
            Booking booking = provider.getHotelDetailsForHotel(hotel);
            if (booking != null) {
                bookingList.add(booking);
            }
        }

        return bookingList;
    }
}
