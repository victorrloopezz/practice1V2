package dacd.lopez;

public interface Subscriber {
    void start(String topicName, Listener listener);
}
