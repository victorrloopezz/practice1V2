package dacd.lopez.view;

import dacd.lopez.controller.AMQTopicsNoDurableSubscriber;
import dacd.lopez.controller.FileDataMartBuilder;
import dacd.lopez.controller.Listener;
import dacd.lopez.controller.Subscriber;

import javax.jms.JMSException;
import java.io.File;

public class DataMartMain {
    private static final String path = "datamart" + File.separator + "eventstore";

    public static void main(String[] args) throws JMSException {
        Subscriber subscriber = new AMQTopicsNoDurableSubscriber(args[0]);
        Listener listener = new FileDataMartBuilder(path);
        subscriber.start(listener);
    }
}
