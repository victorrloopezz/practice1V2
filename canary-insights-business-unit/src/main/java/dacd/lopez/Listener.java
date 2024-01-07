package dacd.lopez;

import java.util.List;

public interface Listener {
    void consume(String message, String topicName);

}
