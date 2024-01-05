package dacd.lopez.model;

import com.google.gson.annotations.SerializedName;

public class WeatherEvent {
    public static class Location {
        public double latitude;
        public double longitude;
        public String name;
    }

    private Location location;

    private double temperature;
    public int humidity;
    private double rain;
    public int clouds;

    @SerializedName("windSpeed")
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

    @SerializedName("predictionTs")
    public String predictionTimestamp;

    public String ss;
    public String ts;
}
