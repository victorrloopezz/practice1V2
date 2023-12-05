package dacd.lopez.control;

import javax.jms.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import dacd.lopez.model.Weather;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.time.Instant;

public class JmsWeatherStore implements WeatherStore {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static String topicName = " prediction.Weather";

    @Override
    public void save(Weather weather) {
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

            String weatherJson = gson.toJson(weather);
            TextMessage weatherMessage = session.createTextMessage(weatherJson);

            producer.send(weatherMessage);

            System.out.println("Weather sent to broker:" + weatherJson);

            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
