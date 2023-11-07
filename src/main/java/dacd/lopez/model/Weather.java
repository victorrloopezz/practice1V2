package dacd.lopez.model;

public class Weather {
    private final Cloud cloud;
    private final Wind wind;
    private final Temp temp;
    private final Rain rain;
    private final int dt;

    public Weather(Cloud cloud, Wind wind, Temp temp, Rain rain, int dt) {
        this.cloud = cloud;
        this.wind = wind;
        this.temp = temp;
        this.rain = rain;
        this.dt = dt;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public Wind getWind() {
        return wind;
    }

    public Temp getTemp() {
        return temp;
    }

    public Rain getRain() {
        return rain;
    }

    public int getDt() {
        return dt;
    }
}