package dacd.lopez.control;

import com.google.gson.Gson;
import dacd.lopez.model.Destination;
import dacd.lopez.model.HotelReservationEvent;
import dacd.lopez.model.WeatherEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BusinessUnit {
    public static void main(String[] args) {
        String datamartPath = "datamart" + File.separator + "eventstore";

        List<String> allData = readAllData(datamartPath);

        Gson gson = new Gson();

        List<Destination> destinations = new ArrayList<>();

        System.out.println("Total de eventos leídos: " + allData.size());

        for (String data : allData) {
            System.out.println("Evento leído: " + data);

            if (data.contains("location") && data.contains("temperature")) {
                // Es un evento meteorológico
                WeatherEvent weatherEvent = gson.fromJson(data, WeatherEvent.class);
                System.out.println("Temperature: " + weatherEvent.getTemperature());
                // Resto del procesamiento para eventos meteorológicos...
                if (isIdealWeather(weatherEvent)) {
                    destinations.add(new Destination(
                            weatherEvent.getLocation().name,
                            weatherEvent.getTemperature(),
                            weatherEvent.getRain(),
                            weatherEvent.getWindSpeed(),
                            0 // Puedes ajustar esta parte según tus necesidades
                    ));
                }
            } else if (data.contains("check_in") && data.contains("rates") && data.contains("hotel")) {
                // Es un evento de reserva de hotel
                HotelReservationEvent hotelReservationEvent = gson.fromJson(data, HotelReservationEvent.class);
                System.out.println("Check-in: " + hotelReservationEvent.check_in);
                // Resto del procesamiento para eventos de reserva de hotel...
                if (hotelReservationEvent.ratesContainer != null && !hotelReservationEvent.ratesContainer.myArrayList.isEmpty()) {
                    double minRate = hotelReservationEvent.ratesContainer.myArrayList.stream()
                            .mapToDouble(rate -> rate.rate + rate.tax)
                            .min()
                            .orElse(Double.MAX_VALUE);

                    destinations.add(new Destination(
                            hotelReservationEvent.hotel.location,
                            0, // Puedes ajustar esta parte según tus necesidades
                            0, // Puedes ajustar esta parte según tus necesidades
                            0, // Puedes ajustar esta parte según tus necesidades
                            minRate
                    ));
                }
            } else {
                System.out.println("Tipo de evento desconocido.");
            }
        }

        System.out.println("Total de destinos encontrados: " + destinations.size());

        // Ordena los destinos por temperatura ascendente y tarifas del hotel ascendente
        List<Destination> sortedDestinations = destinations.stream()
                .sorted(Comparator.comparingDouble(Destination::getTemperature)
                        .thenComparing(Comparator.comparingDouble(Destination::getHotelRate)))
                .collect(Collectors.toList());

        // Imprime los 3 mejores destinos
        int count = Math.min(3, sortedDestinations.size());
        for (int i = 0; i < count; i++) {
            Destination destination = sortedDestinations.get(i);
            System.out.println("Destination: " + destination.getLocation());
            System.out.println("Temperature: " + destination.getTemperature());
            System.out.println("Rain: " + destination.getRain());
            System.out.println("Wind Speed: " + destination.getWindSpeed());
            System.out.println("Hotel Rate: " + destination.getHotelRate());
            System.out.println();
        }
    }

    private static boolean isIdealWeather(WeatherEvent weatherEvent) {
        return weatherEvent.getTemperature() >= 20 && weatherEvent.getTemperature() <= 25 &&
                weatherEvent.getRain() == 0 && weatherEvent.getWindSpeed() < 4;
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
}
