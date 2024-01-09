package dacd.lopez;

import dacd.lopez.controller.AMQSubscriber;
import dacd.lopez.controller.FileDataMartBuilder;
import dacd.lopez.controller.Listener;
import dacd.lopez.view.UserInterface;

import javax.jms.JMSException;
import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String path = "datamart" + File.separator + "eventstore";

    public static void main(String[] args) {
        try {
            AMQSubscriber amqSubscriber = new AMQSubscriber(args[0]);
            FileDataMartBuilder dataMartBuilder = new FileDataMartBuilder(path);
            UserInterface ui = new UserInterface();

            CountDownLatch latch = new CountDownLatch(1);

            Listener dataMartListener = new Listener() {
                @Override
                public void consume(String message, String topicName) {
                    dataMartBuilder.consume(message, topicName);
                    latch.countDown();
                }
            };
            amqSubscriber.start(dataMartListener);

            if (!latch.await(10, TimeUnit.SECONDS)) {
                System.out.println("\n");
                System.out.println("No messages were received within the specified time. Accessing the command line..");
                System.out.println("\n");
            }

            Thread.sleep(5000);

            amqSubscriber.close();

            System.out.println("\n");

            ui.chooseLocation();

        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
