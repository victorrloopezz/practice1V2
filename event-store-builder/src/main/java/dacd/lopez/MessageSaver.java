package dacd.lopez;

import dacd.lopez.model.Weather;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageSaver {
    private String baseDirectory;

    public MessageSaver(String baseDirectory) {
        this.baseDirectory = baseDirectory;
        createDirectoryIfNotExists();
    }

    public void receiveAndSaveMessage(String weatherJson) {
        try {
            saveMessage(getEventFilePath(), weatherJson);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error receiving and saving message: " + e.getMessage());
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

    private String getEventFilePath() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();

        // Crear la estructura de directorios: eventstore/prediction.Weather/{ss}/{YYYYMMDD}.events
        String subDirectory = baseDirectory + "/prediction.Weather/" + Weather.getSs() + "/" + dateFormat.format(date);
        createDirectoryIfNotExists(subDirectory);

        return subDirectory + "/" + System.currentTimeMillis() + ".events";
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

    private void saveMessage(String filePath, String messageContent) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(messageContent);
            System.out.println("Message saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving message to file: " + e.getMessage());
        }
    }
}
