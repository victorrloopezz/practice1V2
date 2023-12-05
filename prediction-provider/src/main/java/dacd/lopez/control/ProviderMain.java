package dacd.lopez.control;

import java.util.TimerTask;
import java.util.Timer;

public class ProviderMain {
    public static void main(String[] args) {

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                WeatherController weatherController = new WeatherController(new OpenWeatherMapProvider(args[0]),
                        new JmsWeatherStore());
                weatherController.execute();
                System.out.println("Program has been updated");
            }
        };
        long period = 6 * 60 * 60 * 1000;
        timer.schedule(task, 0, period);
    }
}
