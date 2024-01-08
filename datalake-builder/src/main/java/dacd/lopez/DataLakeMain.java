package dacd.lopez;

import javax.jms.JMSException;
import java.io.File;

public class DataLakeMain {
    public static void main(String[] args) throws JMSException {

        String activeMQUrl = args[0];
        String rootDirectory = args[1];

        String dataLakePath = rootDirectory + File.separator + "datalake" + File.separator + "eventstore";

        Subscriber subscriber = new AMQTopicsDurableSubscriber(activeMQUrl);
        Listener listener = new FileDataLakeBuilder(dataLakePath);
        subscriber.start(listener);
    }
}
