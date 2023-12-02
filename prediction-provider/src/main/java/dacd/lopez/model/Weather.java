package dacd.lopez.model;

import java.time.Instant;

public class Weather {
    private Double temperature;
    private int humidity;
    private Double rain;
    private int clouds;
    private Double windSpeed;
    private Instant ts;
    private static Instant ss = Instant.now();
    private static String predictionTs = "event-store-builder";

    public Weather(Double temperature, int humidity, Double rain, int clouds, Double windSpeed, Instant ts) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.rain = rain;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        this.ts = ts;
    }

    public Double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public Double getRain() {
        return rain;
    }

    public int getClouds() {
        return clouds;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Instant getTs() {
        return ts;
    }

    public static Instant getSs() {
        return ss;
    }

    public static String getPredictionTs() {
        return predictionTs;
    }

    public String provideParameters() {
        return String.format("Temp: %.2f, Humidity: %d, Rain: %.2f, Clouds: %d, Wind Speed: %.2f",
                temperature, humidity, rain, clouds, windSpeed);
    }

}
