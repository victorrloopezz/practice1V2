package dacd.lopez.model;

import java.time.Instant;

public class Weather {
    private Location location;
    private Double temperature;
    private int humidity;
    private Double rain;
    private int clouds;
    private Double windSpeed;
    private Instant ts;
    private static String ss = "prediction-provider";
    private static Instant predictionTs = Instant.now();

    public Weather(Location location, Double temperature, int humidity, Double rain, int clouds, Double windSpeed, Instant ts) {
        this.location = location;
        this.temperature = temperature;
        this.humidity = humidity;
        this.rain = rain;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        this.ts = ts;
    }

    public static String getSs() {
        return ss;
    }

    public static Instant getPredictionTs() {
        return predictionTs;
    }
}
