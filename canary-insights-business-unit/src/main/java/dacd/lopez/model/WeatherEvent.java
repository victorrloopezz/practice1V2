package dacd.lopez.model;

public class WeatherEvent {
    public static class Location {
        public String name;
    }

    private Location location;

    private double temperature;
    private double rain;
    private double windSpeed;

    public Location getLocation() {
        return location;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getRain() {
        return rain;
    }

    public double getWindSpeed() {
        return windSpeed;
    }
}
