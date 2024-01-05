package dacd.lopez.model;

import dacd.lopez.model.HotelReservationEvent;
import dacd.lopez.model.WeatherEvent;

public class Destination {
    private String location;
    private double temperature;
    private double rain;
    private double windSpeed;
    private double hotelRate;

    public Destination(String location, double temperature, double rain, double windSpeed, double hotelRate) {
        this.location = location;
        this.temperature = temperature;
        this.rain = rain;
        this.windSpeed = windSpeed;
        this.hotelRate = hotelRate;
    }

    public String getLocation() {
        return location;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getRain() {
        return rain;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getHotelRate() {
        return hotelRate;
    }
}
