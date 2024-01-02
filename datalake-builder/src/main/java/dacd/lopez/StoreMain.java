package dacd.lopez;

import javax.jms.JMSException;

public class StoreMain {
    private static final String topicName = "prediction.Weather";
    private static final String path = "datalake/prediction.Weather";
    public static void main(String[] args) throws JMSException {
        Subscriber subscriber = new AMQEventStoreSubscriber(args[0]);
        Listener listener = new FileEventStoreBuilder(path);
        subscriber.start(topicName, listener);
    }
}
