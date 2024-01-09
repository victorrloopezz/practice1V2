package dacd.lopez;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AMQTopicsSubscriber implements Subscriber{
    private final Connection connection;
    private final String clientId = "datalake-builder";
    private final Session session;
    private static final String topicNameWeather = "prediction.Weather";
    private static final String topicNameHotel = "hotel.booking";

    public AMQTopicsSubscriber(String url) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        connection.setClientID(clientId);
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    @Override
    public void start(Listener listener) {
        try {
            Topic destination = session.createTopic(topicNameWeather);
            MessageConsumer consumer = session.createDurableSubscriber(destination, clientId + topicNameWeather);
            consumer.setMessageListener(message -> {
                try {
                    listener.consume(((TextMessage) message).getText(), topicNameWeather);
                    System.out.println(message);
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (JMSException e) {
            throw new RuntimeException("Error setting up MessageListener", e);
        }

        try {
            Topic destination = session.createTopic(topicNameHotel);
            MessageConsumer consumer = session.createDurableSubscriber(destination, clientId + topicNameHotel);
            consumer.setMessageListener(message -> {
                try {
                    listener.consume(((TextMessage) message).getText(), topicNameHotel);
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
