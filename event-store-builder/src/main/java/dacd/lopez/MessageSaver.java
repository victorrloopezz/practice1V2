package dacd.lopez;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MessageSaver {
    private String directoryPath;

    public MessageSaver(String directoryPath) {
        this.directoryPath = directoryPath;
        createDirectoryIfNotExists();
    }

    public void receiveAndSaveMessage(String weatherJson) {
        try {
            saveMessage("weather_message_" + System.currentTimeMillis() + ".json", weatherJson);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error receiving and saving message: " + e.getMessage());
        }
    }

    private void createDirectoryIfNotExists() {
        try {
            if (!Files.exists(Path.of(directoryPath))) {
                Files.createDirectory(Path.of(directoryPath));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error creating directory: " + e.getMessage());
        }
    }

    private void saveMessage(String fileName, String messageContent) {
        String filePath = directoryPath + "/" + fileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(messageContent);
            System.out.println("Message saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving message to file: " + e.getMessage());
        }
    }
}

