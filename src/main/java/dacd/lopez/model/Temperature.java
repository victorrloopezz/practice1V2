package dacd.lopez.model;

public class Temperature {
    private final double temp;
    private final double humidity;

    public Temperature(double temp, double humidity) {
        this.temp = temp;
        this.humidity = humidity;
    }

    public double getTemp() {
        return temp;
    }

    public double getHumidity() {
        return humidity;
    }
}

