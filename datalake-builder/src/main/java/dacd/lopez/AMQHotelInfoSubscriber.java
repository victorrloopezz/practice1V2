package dacd.lopez;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AMQHotelInfoSubscriber implements Subscriber {
    private final Connection connection;
    private final String clientId = "hotel-info-subscriber";
    private final Session session;

    public AMQHotelInfoSubscriber(String url) throws JMSException {
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
