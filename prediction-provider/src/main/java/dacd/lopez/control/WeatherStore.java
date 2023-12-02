package dacd.lopez.control;

import dacd.lopez.model.Location;

import java.time.Instant;

public interface WeatherStore {
    void save(Location iteredLocation, Instant iteredInstant);
}
