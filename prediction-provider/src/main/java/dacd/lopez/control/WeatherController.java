package dacd.lopez.control;

import dacd.lopez.model.Location;
import dacd.lopez.model.Weather;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;

public class WeatherController {
    private final WeatherProvider weatherProvider;
    private final WeatherStore weatherStore;

    public WeatherController(WeatherProvider weatherProvider, WeatherStore weatherStore) {
        this.weatherProvider = weatherProvider;
        this.weatherStore = weatherStore;
    }

    public void execute() {
        List<Location> locationList = createLocationList();
        List<Instant> instantList = createInstantList();

        for (Location location : locationList) {
            for (Instant instant : instantList) {
                Weather weather = weatherProvider.getWeather(location, instant);
                if (weather != null) {
                    displayWeather(weather);
                    weatherStore.save(weather);
                } else {
                    displayNoWeather(location, instant);
                }
            }
        }
    }

    private void displayWeather(Weather weather) {
        System.out.println(weather);
    }

    private void displayNoWeather(Location location, Instant instant) {
        System.out.println("No weather data found for " + location.getName() + " at " + instant);
    }

    private List<Location> createLocationList() {
        return List.of(
                new Location(28.0997300, -15.4134300, "Gran Canaria"),
                new Location(28.46824, -16.25462, "Tenerife"),
                new Location(28.7307900, -13.8674900, "Fuerteventura"),
                new Location(28.4932900, -17.8501300, "La Palma"),
                new Location(27.6851600, -18.0590100, "El Hierro"),
                new Location(29.2523, -13.5091, "La Graciosa"),
                new Location(28.0997300, -15.4134300, "Lanzarote"),
                new Location(28.168611, -17.1966667, "La Gomera")
        );
    }

    private List<Instant> createInstantList() {
        return IntStream.range(0, 5)
                .mapToObj(i -> Instant.now().plus(i, ChronoUnit.DAYS)
                        .truncatedTo(ChronoUnit.DAYS)
                        .plus(12, ChronoUnit.HOURS))
                .collect(Collectors.toList());
    }
}
