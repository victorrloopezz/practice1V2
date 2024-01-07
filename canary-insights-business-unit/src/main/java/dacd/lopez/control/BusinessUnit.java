package dacd.lopez.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BusinessUnit {
    private final Scanner scanner;

    public BusinessUnit() {
        this.scanner = new Scanner(System.in);
    }

    public void chooseLocation() {
        String fileName = generateDataMartFileName();
        List<String> events = readDataMart(fileName);

        System.out.print("Ingrese el nombre de la ubicación (isla): ");
        String locationInput = scanner.nextLine();

        List<String> locationEvents = filterEventsByLocation(events, locationInput);
        if (!locationEvents.isEmpty()) {
            List<double[]> weatherDataDataList = extractWeatherData(locationEvents);
            displayClimaticData(weatherDataDataList);

            List<String> orderHotels = sortHotelsByRate(locationEvents);
            displaySortedHotels(orderHotels);
        } else {
            System.out.println("No hay eventos para la ubicación ingresada.");
        }

        scanner.close();
    }

    private List<String> readDataMart(String filePath) {
        List<String> events = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                events.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + new File(filePath).getAbsolutePath());
            e.printStackTrace();
        }
        return events;
    }

    private List<String> filterEventsByLocation(List<String> events, String location) {
        List<String> eventLocation = new ArrayList<>();
        for (String event : events) {
            if (event.contains("\"name\":\"" + location + "\"") || (event.contains("\"location\":\"" + location + "\""))) {
                eventLocation.add(event);
            }
        }
        return eventLocation;
    }

    private List<double[]> extractWeatherData(List<String> locationEvents) {
        List<double[]> weatherList = new ArrayList<>();

        for (String event : locationEvents) {
            int startIdx = event.indexOf("temperature");
            if (startIdx == -1) {
                continue;
            }

            String[] tokens = event.substring(startIdx).split(",");
            double[] weatherData = new double[5];

            weatherData[0] = Double.parseDouble(tokens[0].split(":")[1].trim());
            weatherData[1] = Double.parseDouble(tokens[1].split(":")[1].trim());
            weatherData[2] = Double.parseDouble(tokens[2].split(":")[1].trim());
            weatherData[3] = Double.parseDouble(tokens[3].split(":")[1].trim());
            weatherData[4] = Double.parseDouble(tokens[4].split(":")[1].replaceAll("[^0-9.]", "").trim()); // Humedad

            weatherList.add(weatherData);
        }

        return weatherList;
    }

    private List<String> sortHotelsByRate(List<String> locationEvents) {
        List<String> bestHotelsSorted = new ArrayList<>();

        for (String event : locationEvents) {
            int ratesIndex = event.indexOf("\"rates\"");
            if (ratesIndex != -1) {
                String ratesSubstring = event.substring(ratesIndex);
                String[] rateTokens = ratesSubstring.split("\"rate\":");
                List<Double> rates = new ArrayList<>();

                for (int i = 1; i < rateTokens.length; i++) {
                    double rate = Double.parseDouble(rateTokens[i].split(",")[0].trim());
                    if (rate > 0) {
                        rates.add(rate);
                    }
                }

                if (!rates.isEmpty()) {
                    double minRate = Collections.min(rates);
                    String hotelName = extractHotelNameFromEvent(event);
                    bestHotelsSorted.add(hotelName + ": " + minRate);
                }
            }
        }

        Collections.sort(bestHotelsSorted, Comparator.comparingDouble(this::extractRateFromHotelEntry));

        return bestHotelsSorted;
    }

    private String extractHotelNameFromEvent(String event) {
        int hotelNameIndex = event.indexOf("\"hotel_name\":\"");
        if (hotelNameIndex != -1) {
            int endIndex = event.indexOf("\",", hotelNameIndex);
            return event.substring(hotelNameIndex + "\"hotel_name\":\"".length(), endIndex);
        }
        return "";
    }

    private double extractRateFromHotelEntry(String hotelEntry) {
        return Double.parseDouble(hotelEntry.split(":")[1].trim());
    }

    private static String generateDataMartFileName() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return "datamart" + File.separator + "eventstore" + File.separator + currentDate.format(formatter)
                + File.separator + "all_events.events";
    }

    private void displayClimaticData(List<double[]> weatherList) {
        System.out.println("\nDatos climáticos de la isla seleccionada:");
        for (double[] weather : weatherList) {
            System.out.println("Temperatura: " + weather[0] + "°C");
            System.out.println("Humedad: " + weather[1]);
            System.out.println("Probabilidad de lluvia: " + weather[2] + "%");
            System.out.println("Nubes: " + weather[3]);
            System.out.println("Velocidad del viento: " + weather[4] + " m/s" + "\n");
        }
    }

    private void displaySortedHotels(List<String> sortedHotels) {
        System.out.println("\nHoteles ordenados por el precio más bajo:");
        for (String hotelEntry : sortedHotels) {
            System.out.println(hotelEntry + "€");
        }
    }
}
