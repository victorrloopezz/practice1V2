package dacd.lopez.model;

import java.time.Instant;

public class Weather {
    private Double temp;
    private int humidity;
    private Double rain;
    private int all;
    private Double speed;
    private Instant weatherInstant;

    public Weather(Double temp, int humidity, Double rain, int all, Double speed, Instant weatherInstant) {
        this.temp = temp;
        this.humidity = humidity;
        this.rain = rain;
        this.all = all;
        this.speed = speed;
        this.weatherInstant = weatherInstant;
    }

    public Double getTemp() {
        return temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public Double getRain() {
        return rain;
    }

    public int getAll() {
        return all;
    }

    public Double getSpeed() {
        return speed;
    }

    public Instant getWeatherInstant() {
        return weatherInstant;
    }
}