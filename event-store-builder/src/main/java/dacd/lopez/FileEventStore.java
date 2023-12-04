package dacd.lopez;

import dacd.lopez.model.Weather;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileEventStore implements EventStore {
    private String baseDirectory;
    private String currentEventFolder;
    private String currentEventFilePath;

    public FileEventStore(String baseDirectory) {
        this.baseDirectory = baseDirectory;
        createDirectoryIfNotExists();
        initializeEventFilePath();
    }

    @Override
    public void saveEvent(String eventJson) {
        try {
            saveEventToFile(eventJson);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error saving event: " + e.getMessage());
        }
    }

    private void createDirectoryIfNotExists() {
        try {
            if (!Files.exists(Path.of(baseDirectory))) {
                Files.createDirectory(Path.of(baseDirectory));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error creating base directory: " + e.getMessage());
        }
    }

    private void initializeEventFilePath() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(Weather.getPredictionTs().toEpochMilli());

        String formattedDate = dateFormat.format(date);
        currentEventFolder = baseDirectory + "/prediction.Weather/" + Weather.getSs();
        createDirectoryIfNotExists(currentEventFolder);

        currentEventFilePath = currentEventFolder + "/" + formattedDate + ".events";
    }


    private void saveEventToFile(String eventJson) throws IOException {
        String filePath = getEventFilePath();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(eventJson);
            writer.newLine();
            System.out.println("Event saved to: " + filePath);
        }
    }

    private String getEventFilePath() {
        return currentEventFilePath;
    }

    private void createDirectoryIfNotExists(String directoryPath) {
        try {
            if (!Files.exists(Path.of(directoryPath))) {
                Files.createDirectories(Path.of(directoryPath));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error creating directory: " + e.getMessage());
        }
    }
}
