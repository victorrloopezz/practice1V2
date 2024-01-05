package dacd.lopez.control;

import javax.jms.JMSException;
import java.io.File;

public class DataMartMain {
    private static final String path = "datamart" + File.separator + "eventstore";

    public static void main(String[] args) throws JMSException {
        Subscriber subscriber = new AMQDataMartSubscriber(args[0]);
        Listener listener = new FileDataMartBuilder(path);
        subscriber.start(listener);
    }
}
