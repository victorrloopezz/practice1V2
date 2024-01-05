package dacd.lopez;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class FileDataLakeBuilder implements Listener {
    private final String path;

    public FileDataLakeBuilder(String path) {
        this.path = path;
    }

    @Override
    public void consume(String message, String topicName) {
        System.out.println("message:" + message);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);

        String ssValue = jsonObject.get("ss").getAsString();
        String tsValue = jsonObject.get("ts").getAsString();

        ZonedDateTime zonedDateTime = ZonedDateTime.parse(tsValue);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = zonedDateTime.format(formatter);

        String directoryPath = path + File.separator + topicName + File.separator + ssValue;
        createDirectory(directoryPath);

        String filePath = directoryPath + File.separator + formattedDate + ".events";
        writeMessageToFile(filePath, message);
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
}