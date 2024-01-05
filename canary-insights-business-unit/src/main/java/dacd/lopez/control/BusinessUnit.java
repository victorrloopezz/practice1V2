package dacd.lopez.control;

import com.google.gson.Gson;
import dacd.lopez.model.Destination;
import dacd.lopez.model.HotelReservationEvent;
import dacd.lopez.model.WeatherEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BusinessUnit {
    private static final int IDEAL_TEMP_LOWER_BOUND = 20;
    private static final int IDEAL_TEMP_UPPER_BOUND = 25;
    private static final int IDEAL_WIND_SPEED = 4;

    public static void main(String[] args) {
        String datamartPath = "datamart" + File.separator + "eventstore";

        List<String> allData = readAllData(datamartPath);

        Gson gson = new Gson();

        List<Destination> destinations = new ArrayList<>();

        for (String data : allData) {

            if (data.contains("location") && data.contains("temperature")) {
                WeatherEvent weatherEvent = gson.fromJson(data, WeatherEvent.class);
                processWeatherEvent(weatherEvent, destinations);
            } else if (data.contains("check_in") && data.contains("rates") && data.contains("hotel")) {
                HotelReservationEvent hotelReservationEvent = gson.fromJson(data, HotelReservationEvent.class);
                processHotelReservationEvent(hotelReservationEvent, destinations);
            } else {
                System.out.println("Tipo de evento desconocido: " + data);
            }
        }

        List<Destination> filteredDestinations = destinations.stream()
                .filter(destination -> destination.getTemperature() >= IDEAL_TEMP_LOWER_BOUND &&
                        destination.getTemperature() <= IDEAL_TEMP_UPPER_BOUND &&
                        destination.getWindSpeed() < IDEAL_WIND_SPEED &&
                        destination.getRain() == 0)
                .collect(Collectors.toList());

        System.out.println("Total de destinos encontrados que cumplen los requisitos: " + filteredDestinations.size());

        for (Destination destination : filteredDestinations) {
            System.out.println("Destino encontrado: " + destination.getLocation());
            System.out.println("Temperature: " + destination.getTemperature());
            System.out.println("Rain: " + destination.getRain());
            System.out.println("Wind Speed: " + destination.getWindSpeed());
            System.out.println("Hotel Rate: " + destination.getHotelRate());
            System.out.println();

            // Buscar el hotel más barato si la ubicación coincide
            findCheapestHotelForDestination(destination, allData, gson);
        }

        // Encontrar el destino que se repite más
        String mostRepeatedDestination = findMostRepeatedDestination(destinations);
        System.out.println("Destino con mejor clima: " + mostRepeatedDestination);
    }

    private static void processWeatherEvent(WeatherEvent weatherEvent, List<Destination> destinations) {
        if (isIdealWeather(weatherEvent)) {
            destinations.add(new Destination(
                    weatherEvent.getLocation().name,
                    weatherEvent.getTemperature(),
                    weatherEvent.getRain(),
                    weatherEvent.getWindSpeed(),
                    0
            ));
        }
    }

    private static void processHotelReservationEvent(HotelReservationEvent hotelReservationEvent, List<Destination> destinations) {
        if (hotelReservationEvent.ratesContainer != null && !hotelReservationEvent.ratesContainer.myArrayList.isEmpty()) {
            double minRate = hotelReservationEvent.ratesContainer.myArrayList.stream()
                    .mapToDouble(rate -> rate.getRate() + rate.getTax())
                    .min()
                    .orElse(Double.MAX_VALUE);

            destinations.add(new Destination(
                    hotelReservationEvent.getHotel().getLocation(),
                    0, // Puedes ajustar esta parte según tus necesidades
                    0, // Puedes ajustar esta parte según tus necesidades
                    0, // Puedes ajustar esta parte según tus necesidades
                    minRate
            ));
        }
    }

    private static boolean isIdealWeather(WeatherEvent weatherEvent) {
        return weatherEvent.getTemperature() >= IDEAL_TEMP_LOWER_BOUND &&
                weatherEvent.getTemperature() <= IDEAL_TEMP_UPPER_BOUND &&
                weatherEvent.getRain() == 0 && weatherEvent.getWindSpeed() < IDEAL_WIND_SPEED;
    }

    private static List<String> readAllData(String datamartPath) {
        List<String> allData = new ArrayList<>();
        File baseDirectory = new File(datamartPath);
        File[] subdirectories = baseDirectory.listFiles(File::isDirectory);

        if (subdirectories != null) {
            for (File subdirectory : subdirectories) {
                File[] files = subdirectory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        List<String> fileData = readFile(file);
                        allData.addAll(fileData);
                    }
                }
            }
        }

        return allData;
    }

    private static List<String> readFile(File file) {
        List<String> fileData = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileData.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileData;
    }

    private static String findMostRepeatedDestination(List<Destination> destinations) {
        Map<String, Integer> destinationCountMap = new HashMap<>();

        for (Destination destination : destinations) {
            destinationCountMap.put(destination.getLocation(), destinationCountMap.getOrDefault(destination.getLocation(), 0) + 1);
        }

        return destinationCountMap.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse("Ninguno");
    }

    private static void findCheapestHotelForDestination(Destination destination, List<String> allData, Gson gson) {
        Map<String, Double> cheapestRates = new HashMap<>();

        for (String data : allData) {
            if (data.contains("check_in") && data.contains("rates") && data.contains("hotel")) {
                HotelReservationEvent hotelReservationEvent = gson.fromJson(data, HotelReservationEvent.class);
                if (hotelReservationEvent.getHotel().getLocation().equals(destination.getLocation()) &&
                        hotelReservationEvent.ratesContainer != null && !hotelReservationEvent.ratesContainer.myArrayList.isEmpty()) {

                    HotelReservationEvent.Rate cheapestRate = hotelReservationEvent.ratesContainer.myArrayList.stream()
                            .min(Comparator.comparingDouble(rate -> rate.getRate()))
                            .orElse(null);

                    if (cheapestRate != null) {
                        String hotelKey = hotelReservationEvent.getHotel().getHotel_key();
                        double currentCheapestRate = cheapestRates.getOrDefault(hotelKey, Double.MAX_VALUE);

                        if (cheapestRate.getRate() < currentCheapestRate) {
                            cheapestRates.put(hotelKey, cheapestRate.getRate());

                            System.out.println("Hotel encontrado para " + destination.getLocation());
                            System.out.println("Check-in: " + hotelReservationEvent.getCheck_in());
                            System.out.println("Check-out: " + hotelReservationEvent.getCheck_out());
                            System.out.println("Nombre del hotel: " + hotelReservationEvent.getHotel().getHotel_name());
                            System.out.println("Tarifa más barata: " + cheapestRate.getRate());
                            System.out.println();
                        }
                    }
                }
            }
        }
    }
}
