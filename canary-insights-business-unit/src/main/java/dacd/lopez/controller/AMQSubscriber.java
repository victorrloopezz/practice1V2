package dacd.lopez.controller;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AMQSubscriber implements Subscriber {
    private final Connection connection;
    private final String clientId = "datamart-builder";
    private final Session session;
    private static final String topicNameWeather = "prediction.Weather";
    private static final String topicNameHotel = "hotel.booking";


    public AMQSubscriber(String url) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        connection.setClientID(clientId);
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    @Override
    public void start(Listener listener) {
        try {
            Topic destinationWeather = session.createTopic(topicNameWeather);
            MessageConsumer consumerWeather = session.createDurableSubscriber(destinationWeather, clientId + topicNameWeather);
            consumerWeather.setMessageListener(message -> {
                try {
                    listener.consume(((TextMessage) message).getText(), topicNameWeather);
                    System.out.println(message);
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (JMSException e) {
            throw new RuntimeException("Error setting up MessageListener for weather", e);
        }

        try {
            Topic destinationHotel = session.createTopic(topicNameHotel);
            MessageConsumer consumerHotel = session.createDurableSubscriber(destinationHotel, clientId + topicNameHotel);
            consumerHotel.setMessageListener(message -> {
                try {
                    listener.consume(((TextMessage) message).getText(), topicNameHotel);
                    System.out.println(message);
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (JMSException e) {
            throw new RuntimeException("Error setting up MessageListener for hotel", e);
        }
    }
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            throw new RuntimeException("Error closing JMS connection", e);
        }
    }
}
