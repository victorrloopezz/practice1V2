package dacd.lopez.controller;

public interface Listener {
    void consume(String message, String topicName);

}
