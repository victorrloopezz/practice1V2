package dacd.lopez.control;

import dacd.lopez.model.Location;
import dacd.lopez.model.Weather;

import java.time.Instant;


public interface WeatherStore {
    void save(Weather weather);

    void load(Location location, Instant instant);
}
