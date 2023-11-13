package dacd.lopez.model;

import java.time.Instant;

public class Weather {
    private Double temp;
    private int humidity;
    private Double rain;
    private int all;
    private Double speed;
    private Instant ts;

    public Weather(Double temp, int humidity, Double rain, int all, Double speed, Instant ts) {
        this.temp = temp;
        this.humidity = humidity;
        this.rain = rain;
        this.all = all;
        this.speed = speed;
        this.ts = ts;
    }
    public String provideParameters() {
        return String.format("Temp: %.2f, Humidity: %d, Rain: %.2f, Clouds: %d, Wind Speed: %.2f",
                temp, humidity, rain, all, speed);
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

    public Instant getTs() {
        return ts;
    }
}
