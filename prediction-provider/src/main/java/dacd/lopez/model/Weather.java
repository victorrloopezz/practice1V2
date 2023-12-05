package dacd.lopez.model;

import java.time.Instant;

public class Weather {
    private Location location;
    private Double temperature;
    private int humidity;
    private Double rain;
    private int clouds;
    private Double windSpeed;
    private Instant predictionTs;
    private final String ss;
    private final Instant ts;

    public Weather(Location location, Double temperature, int humidity, Double rain, int clouds, Double windSpeed, Instant predictionTs) {
        this.location = location;
        this.temperature = temperature;
        this.humidity = humidity;
        this.rain = rain;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        this.predictionTs = predictionTs;
        this.ss = "prediction-provider";
        this.ts = Instant.now();
    }
}
