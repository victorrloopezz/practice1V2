package dacd.lopez.control;
import com.google.gson.Gson;
import dacd.lopez.model.List;
import dacd.lopez.model.Weather;
import org.jsoup.Jsoup;

import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        try {
            String url = "https://api.openweathermap.org/data/2.5/forecast?lat=28.0997300&lon=-15.4134300&appid=080f891f7c9879d5902167d17af70f62";
            String jsonString = Jsoup.connect(url).ignoreContentType(true).execute().body();

            Gson gson = new Gson();
            List weatherList = gson.fromJson(jsonString, List.class);


            for (Weather weather : weatherList.getList()) {
                System.out.println("Clouds:" + weather.getClouds().getAll() + "\n" +
                        "Temperature:" + weather.getMain().getTemp() + "\n" +
                        "Humidity:" + weather.getMain().getHumidity() + "\n" +
                        "Wind speed:" + weather.getWind().getSpeed());
                if (weather.getRain() == null) {
                    System.out.println("No rain\n");
                } else {
                    System.out.println("Pop: " + weather.getRain().getPop() + "\n");
                }
            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
