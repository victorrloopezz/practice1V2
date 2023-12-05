package dacd.lopez;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AMQTopicSubscriber implements Subscriber{
    private final String url;
    private final Connection connection;
    private static String clientId = "event-store-builder";
    private final Session session;

    public AMQTopicSubscriber(String url) throws JMSException {
        this.url = url;
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        connection.setClientID(clientId);
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }


    @Override
    public void start(String topicName, Listener listener) {
        try {
            Topic destination = session.createTopic(topicName);
            MessageConsumer consumer = session.createDurableSubscriber(destination, clientId + topicName);
            consumer.setMessageListener(message -> {
                try {
                    listener.consume(((TextMessage) message).getText());
                    System.out.println(message);
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (JMSException e) {
            throw new RuntimeException("Error setting up MessageListener", e);
        }
    }

}