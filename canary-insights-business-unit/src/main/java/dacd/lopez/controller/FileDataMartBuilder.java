package dacd.lopez.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class FileDataMartBuilder implements Listener {
    private final String path;
    private final String fileName = "all_events";
    private static boolean datamartCleared = false;

    public FileDataMartBuilder(String path) {
        this.path = path;
    }

    @Override
    public void consume(String message, String topicName) {
        System.out.println("message:" + message);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);

        String tsValue = jsonObject.get("ts").getAsString();

        ZonedDateTime zonedDateTime = ZonedDateTime.parse(tsValue);

        if (zonedDateTime.toLocalDate().equals(LocalDate.now())) {
            if (!datamartCleared) {
                clearDataMart();
                datamartCleared = true;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String formattedDate = zonedDateTime.format(formatter);

            String directoryPath = path + File.separator + formattedDate;
            createDirectory(directoryPath);

            cleanOldEvents(path, formattedDate);

            String filePath = directoryPath + File.separator + fileName + ".events";
            writeMessageToFile(filePath, message);
        }
    }

    private void clearDataMart() {
        File baseDirectory = new File(path);
        File[] subdirectories = baseDirectory.listFiles(File::isDirectory);

        if (subdirectories != null) {
            for (File subdirectory : subdirectories) {
                deleteDirectory(subdirectory);
            }
        }
    }

    private void createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
            System.out.println("Directory created");
        }
    }

    private void writeMessageToFile(String filePath, String message) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(message + "\n");
            System.out.println("Message written: " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file", e);
        }
    }

    private void cleanOldEvents(String basePath, String currentFolder) {
        File baseDirectory = new File(basePath);
        File[] subdirectories = baseDirectory.listFiles(File::isDirectory);

        if (subdirectories != null) {
            for (File subdirectory : subdirectories) {
                if (!subdirectory.getName().equals(currentFolder)) {
                    deleteDirectory(subdirectory);
                }
            }
        }
    }

    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }
}
