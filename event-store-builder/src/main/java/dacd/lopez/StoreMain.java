package dacd.lopez;

import javax.jms.JMSException;

public class StoreMain {
    private static final String topicName = "prediction.Weather";
    private static final String path = "eventstore/prediction.Weather";
    public static void main(String[] args) throws JMSException {
        Subscriber subscriber = new AMQTopicSubscriber(args[0]);
        Listener listener = new FileEventStoreBuilder(path);
        subscriber.start(topicName, listener);
    }
}
