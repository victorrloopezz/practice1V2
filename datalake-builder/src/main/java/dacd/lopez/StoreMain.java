package dacd.lopez;

import javax.jms.JMSException;
import java.io.File;
public class StoreMain {
    private static final String path = "datalake" + File.separator + "eventstore";
    public static void main(String[] args) throws JMSException {
        Subscriber subscriber = new AMQEventStoreSubscriber(args[0]);
        Listener listener = new FileEventStoreBuilder(path);
        subscriber.start(listener);
    }
}
