package dacd.lopez.control;

import java.util.List;

public interface Listener {
    void consume(String message, String topicName);

}
