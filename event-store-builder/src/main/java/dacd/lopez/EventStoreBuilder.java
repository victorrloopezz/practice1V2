package dacd.lopez;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class EventStoreBuilder {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static String topicName = "prediction.Weather";
    private static String clientId = "Lopez";
    private static String subscriptionName = "LopezSubscription";
    private static String eventStoreDirectory = "event-store-builder";

    public static void main(String[] args) {
        try {
            // Crear una conexión al broker
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.setClientID(clientId); // Establecer el identificador de cliente
            connection.start();

            // Crear una sesión sin transacciones y con auto-acknowledge
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Crear una cola temporal
            TemporaryQueue tempQueue = session.createTemporaryQueue();

            // Crear un suscriptor durable para el tópico
            MessageConsumer consumer = session.createDurableSubscriber(session.createTopic(topicName), subscriptionName);

            // Crear un productor para la cola temporal
            createEventStoreDirectory();

            System.out.println("\n");
            // Iniciar la escucha de mensajes
            consumer.setMessageListener(message -> {
                try {
                    String weatherJson = ((TextMessage) message).getText();
                    System.out.println("Message recived" + weatherJson);
                    saveToEventStore(weatherJson);
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private static void createEventStoreDirectory() {
        // Implementar la lógica para crear el directorio del event store si no existe
    }

    private static void saveToEventStore(String weatherJson) {
        // Implementar la lógica para guardar el JSON en el event store según la estructura de directorio deseada
    }
}
