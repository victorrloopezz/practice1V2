package dacd.lopez;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmrWeatherStore {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static String topicName = "prediction.Weather";
    private static String clientId = "Lopez";
    private static String subscriptionName = "LopezSubscription";
    private static String directoryPath = "messages_directory";

    public static void main(String[] args) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.setClientID(clientId);
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageConsumer consumer = session.createDurableSubscriber(session.createTopic(topicName), subscriptionName);

            System.out.println("\n");

            // Crear una instancia de MessageReceiver
            MessageSaver messageSaver = new MessageSaver(directoryPath);

            consumer.setMessageListener(message -> {
                try {
                    String weatherJson = ((TextMessage) message).getText();
                    System.out.println("Message received: " + weatherJson);
                    messageSaver.receiveAndSaveMessage(weatherJson);
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
