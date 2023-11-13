package dacd.lopez.control;

import dacd.lopez.model.Weather;
import dacd.lopez.model.Location;

import java.time.Instant;

public interface WeatherProvider {
     Weather getWeather(Location location, Instant instant);
}
