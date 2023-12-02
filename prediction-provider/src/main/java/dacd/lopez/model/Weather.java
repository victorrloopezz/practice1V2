package dacd.lopez.model;

import java.time.Instant;

public class Weather {
    private Double temperature;
    private int humidity;
    private Double rain;
    private int clouds;
    private Double windSpeed;
    private Instant ts;
    private static String ss = "event-store-builder";
    private static Instant predictionTs = Instant.now();

    public Weather(Double temperature, int humidity, Double rain, int clouds, Double windSpeed, Instant ts) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.rain = rain;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        this.ts = ts;
    }

    public String provideParameters() {
        return String.format("Temp: %.2f, Humidity: %d, Rain: %.2f, Clouds: %d, Wind Speed: %.2f",
                temperature, humidity, rain, clouds, windSpeed);
    }

}
