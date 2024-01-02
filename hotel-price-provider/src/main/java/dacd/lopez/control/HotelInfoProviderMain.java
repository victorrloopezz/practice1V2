package dacd.lopez.control;
import java.util.Timer;
import java.util.TimerTask;

public class HotelInfoProviderMain {
    public static void main(String[] args) {
        JmsHotelInfoSender jmsSender = new JmsHotelInfoSender();
        HotelController hotelController = new HotelController(jmsSender);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                hotelController.execute();
                System.out.println("Program has been updated");
            }
        };

        long period = 6 * 60 * 60 * 1000;
        timer.schedule(task, 0, period);
    }
}
