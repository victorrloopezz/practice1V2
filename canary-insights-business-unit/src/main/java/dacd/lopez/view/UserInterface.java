package dacd.lopez.view;

import java.util.Scanner;
import java.util.List;
import java.util.Date;
import dacd.lopez.model.BusinessUnit;

public class UserInterface {
    private final Scanner scanner;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }

    public void chooseLocation() {
        String fileName = BusinessUnit.generateDataMartFileName();
        List<String> events = BusinessUnit.readDataMart(fileName);

        System.out.print("Choose your island destination: ");
        String inputLocation = scanner.nextLine();

        List<String> locationEvents = BusinessUnit.filterEventsByLocation(events, inputLocation);
        if (!locationEvents.isEmpty()) {

            List<double[]> weatherList = BusinessUnit.extractWeather(locationEvents);
            displayWeather(weatherList);

            List<String> sortedHotels = BusinessUnit.sortHotelsByRate(locationEvents);
            displaySortedHotels(sortedHotels);
        } else {
            System.out.println("No events for this island");
        }

        scanner.close();
    }

    private void displayWeather(List<double[]> weatherList) {
        System.out.println("\nWeather:");
        for (double[] weather : weatherList) {
            System.out.println("Prediction date: " + BusinessUnit.predictionTsFormat.format(new Date((long) weather[5])));
            System.out.println("Temperature: " + weather[0] + "°C");
            System.out.println("Humidity: " + weather[1]);
            System.out.println("Rain probability: " + weather[2] + "%");
            System.out.println("Clouds: " + weather[3]);
            System.out.println("Wind Speed: " + weather[4] + " m/s" + "\n");
        }
    }

    private void displaySortedHotels(List<String> sortedHotels) {
        System.out.println("\nHotels sorted by price:");
        for (String hotelEntry : sortedHotels) {
            System.out.println(hotelEntry);
        }
    }
}
