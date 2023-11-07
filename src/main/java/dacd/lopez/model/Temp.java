package dacd.lopez.model;

public class Temp {
    private final double temperature;
    private final double humidity;

    public Temp(double temperature, double humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }
}

