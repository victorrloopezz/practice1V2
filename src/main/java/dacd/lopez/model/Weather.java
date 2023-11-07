package dacd.lopez.model;
import java.time.Instant;

public class Weather {
    private final Cloud clouds;
    private final Wind wind;
    private final Temperature main;
    private final Rain rain;
    private final int dt;

    public Weather(Cloud clouds, Wind wind, Temperature main, Rain rain, int dt) {
        this.clouds = clouds;
        this.wind = wind;
        this.main = main;
        this.rain = rain;
        this.dt = dt;
    }

    public Cloud getClouds() {
        return clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public Temperature getMain() {
        return main;
    }

    public Rain getRain() {
        return rain;
    }

    public int getDt() {
        return dt;
    }
}