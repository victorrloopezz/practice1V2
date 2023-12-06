package dacd.lopez;

import javax.jms.JMSException;

public class StoreMain {
    public static void main(String[] args) throws JMSException {
        Subscriber subscriber = new AMQTopicSubscriber(args[0]);
        Listener listener = new FileEventStoreBuilder(args[2]);
        subscriber.start(args[1], listener);
    }
}
