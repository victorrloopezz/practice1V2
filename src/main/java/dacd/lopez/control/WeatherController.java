package dacd.lopez.control;

import dacd.lopez.model.Location;
import dacd.lopez.model.Weather;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class WeatherController {
    public WeatherProvider weatherProvider;

    public WeatherController(WeatherProvider weatherProvider) {
        this.weatherProvider = weatherProvider;
    }

    private void execute() {
        Location granCanaria = new Location(28.0997300, -15.4134300, "Las Palmas de Gran Canaria");
        Location tenerife = new Location(28.46824, -16.25462, "Santa Cruz de Tenerife");
        Location fuerteventura = new Location(28.7307900, -13.8674900, "Corralejo - Fuerteventura");
        Location laPalma = new Location(28.4932900, -17.8501300, "Fuencaliente de La Palma");
        Location elHierro = new Location(27.6851600, -18.0590100, "El Pinar de El Hierro");
        Location laGraciosa = new Location(29.2523, -13.5091, "La Graciosa");
        Location lanzarote = new Location(28.0997300, -15.4134300, "Tiagua - Lnazarote");
        Location laGomera = new Location(28.168611, -17.1966667, "Hermigua - La Gomera");

        List<Location> locationList = List.of(granCanaria, tenerife, fuerteventura, laPalma, elHierro,
                laGraciosa, lanzarote, laGomera);

        ArrayList<Instant> instantList = new ArrayList<>();
        ArrayList<Weather> weatherArrayList = new ArrayList<>();

        createInstant(instantList);
        getWeatherCall(instantList, locationList, weatherArrayList);
        //loadCall(instantList, locationList);
    }

    public static ArrayList<Instant> createInstant(ArrayList<Instant> instants) {
        for (int i = 0; i < 5; i++) {
            LocalDate today = LocalDate.now();
            LocalTime hour = LocalTime.of(12, 0);
            LocalDateTime todayHour = LocalDateTime.of(today, hour);
            Instant previusInstant = todayHour.toInstant(ZoneOffset.UTC);
            Instant nextInstant = previusInstant.plus(i, ChronoUnit.DAYS);
            instants.add(nextInstant);
        }
        return instants;
    }

    public static ArrayList<Weather> getWeatherCall(ArrayList<Instant> instantList, List<Location> locationList,
                                                    ArrayList<Weather> weatherArrayList) {
        WeatherProvider weatherProvider = new OpenWeatherMapProvider();

        for (Location iteredLocation : locationList) {
            for (Instant iteredInstant : instantList) {
                Weather weather = WeatherProvider.WeatherGet(iteredLocation, iteredInstant);

                if (weather != null) {
                    System.out.println("Weather for " + iteredLocation.getName() + " at " + iteredInstant + ":");
                    System.out.println(weather.provideParameters());
                    System.out.println("\n");
                } else {
                    System.out.println("No weather data found for " + iteredLocation.getName() + " at " + iteredInstant);
                }
                weatherArrayList.add(weather);
            }
            System.out.println("\n");
        }
        return weatherArrayList;
    }

    //public static void loadCall(ArrayList<Instant> instantList, List<Location> locationList){
    //WeatherStore weatherStore = new SqliteWeatherStore();
    //for (Location iteredLocation : locationList) {
    //for (Instant iteredInstant : instantList) {
    // weatherStore.load(iteredLocation, iteredInstant);
    //}
    //}
    //}

    public static void main(String[] args) {
        WeatherController weatherController = new WeatherController(new OpenWeatherMapProvider());
        weatherController.execute();
    }
}