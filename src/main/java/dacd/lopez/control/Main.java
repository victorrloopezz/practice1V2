package dacd.lopez.control;

import com.google.gson.Gson;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dacd.lopez.model.Weather;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Main {
    public static void main(String[] args) {
        try {
            String url = "https://api.openweathermap.org/data/2.5/forecast?lat=28.0997300&lon=-15.4134300&appid=080f891f7c9879d5902167d17af70f62";
            String jsonString = Jsoup.connect(url).ignoreContentType(true).execute().body();

            Gson gson = new Gson();
            JsonObject weathers = gson.fromJson(jsonString, JsonObject.class);
            JsonArray list = weathers.getAsJsonObject().getAsJsonArray("list");

            System.out.println(weathers);
            System.out.println(list);

            List<Weather> weatherList = new ArrayList<>();
            System.out.println(weatherList);

            for (JsonElement element : list) {
                JsonObject weather = element.getAsJsonObject();

                JsonObject main = weather.get("main").getAsJsonObject();
                Double temp = main.get("temp").getAsDouble();

                Double rain = weather.get("pop").getAsDouble();

                int humidity = main.get("humidity").getAsInt();

                JsonObject clouds = weather.get("clouds").getAsJsonObject();
                int all = clouds.get("all").getAsInt();

                JsonObject wind = weather.get("wind").getAsJsonObject();
                Double speed = wind.get("speed").getAsDouble();

                long dt = weather.get("dt").getAsLong();

                long unixTimestamp = dt;
                Instant weatherInstant = Instant.ofEpochSecond(unixTimestamp);

                Weather weatherObject = new Weather(temp, humidity, rain, all, speed, weatherInstant);
                weatherList.add(weatherObject);
            }

            for (Weather weatherIter : weatherList) {
                System.out.println("Temp: " + weatherIter.getTemp() + ", Pop: " + weatherIter.getRain() +
                        ", Humidity: " + weatherIter.getHumidity() + ", Clouds: " + weatherIter.getAll() +
                        ", Wind Speed: " + weatherIter.getSpeed() + ", Date&Hour: " + weatherIter.getWeatherInstant());
            }


        }catch (Exception exception){
            exception.printStackTrace();
        }

    }
}
