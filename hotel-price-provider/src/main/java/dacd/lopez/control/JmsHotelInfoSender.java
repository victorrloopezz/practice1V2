package dacd.lopez.control;

import javax.jms.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import dacd.lopez.model.Booking;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.time.Instant;

public class JmsHotelInfoSender implements HotelInfoSender {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static String topicName = "hotel.booking";

    @Override
    public void send(Booking booking) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createTopic(topicName);

            MessageProducer producer = session.createProducer(destination);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Instant.class, (JsonSerializer<Instant>) (src, typeOfSrc, context) ->
                            context.serialize(src.toString()))
                    .create();

            String bookingJson = gson.toJson(booking);
            TextMessage bookingMessage = session.createTextMessage(bookingJson);

            producer.send(bookingMessage);

            System.out.println("Booking details sent to broker: " + bookingJson);

            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
