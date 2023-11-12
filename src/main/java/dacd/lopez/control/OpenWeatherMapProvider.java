package dacd.lopez.control;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dacd.lopez.model.Weather;
import org.jsoup.Jsoup;

import dacd.lopez.model.Location;

import java.io.IOException;
import java.time.Instant;

public class OpenWeatherMapProvider implements WeatherProvider {
    public Weather WeatherGet(Location location, Instant instant){
        Weather weatherObject = null;
        try {
            String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + location.getLatitude() + "&lon=" +
                    location.getLongitude() + "&appid=080f891f7c9879d5902167d17af70f62&units=metric";

            String jsonString = Jsoup.connect(url).ignoreContentType(true).execute().body();

            Gson gson = new Gson();
            JsonObject apiContent = gson.fromJson(jsonString, JsonObject.class);
            JsonArray weatherArray = apiContent.getAsJsonObject().getAsJsonArray("list");

            for (JsonElement element : weatherArray) {
                JsonObject weather = element.getAsJsonObject();

                JsonObject main = weather.get("main").getAsJsonObject();
                Double temp = main.get("temp").getAsDouble();

                Double rain = weather.get("pop").getAsDouble();

                int humidity = main.get("humidity").getAsInt();

                JsonObject clouds = weather.get("clouds").getAsJsonObject();
                int all = clouds.get("all").getAsInt();

                JsonObject wind = weather.get("wind").getAsJsonObject();
                Double speed = wind.get("speed").getAsDouble();

                long ts = weather.get("dt").getAsLong();

                long unixTimestamp = ts;
                Instant weatherInstant = Instant.ofEpochSecond(unixTimestamp);

                if (weatherInstant.equals(instant)) {
                    weatherObject = new Weather(temp, humidity, rain, all, speed, location, weatherInstant);
                    break;
                }
            }
        }
        catch (IOException exception){
            throw new RuntimeException(exception);
        }
        return weatherObject;
}
}
