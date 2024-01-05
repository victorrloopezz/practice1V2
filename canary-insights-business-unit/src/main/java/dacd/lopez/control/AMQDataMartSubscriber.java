package dacd.lopez.control;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AMQDataMartSubscriber implements Subscriber {
    private final Connection connection;
    private final Session session;
    private static final String topicNameWeather = "prediction.Weather";
    private static final String topicNameHotel = "hotel.booking";

    public AMQDataMartSubscriber(String url) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    @Override
    public void start(Listener listener) {
        try {
            Topic weatherDestination = session.createTopic(topicNameWeather);
            Topic hotelDestination = session.createTopic(topicNameHotel);

            MessageConsumer weatherConsumer = session.createConsumer(weatherDestination);
            MessageConsumer hotelConsumer = session.createConsumer(hotelDestination);

            weatherConsumer.setMessageListener(message -> handleMessage(message, listener, topicNameWeather));
            hotelConsumer.setMessageListener(message -> handleMessage(message, listener, topicNameHotel));
        } catch (JMSException e) {
            throw new RuntimeException("Error setting up MessageListener", e);
        }
    }

    private void handleMessage(Message message, Listener listener, String topicName) {
        try {
            listener.consume(((TextMessage) message).getText(), topicName);
            System.out.println(message);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
