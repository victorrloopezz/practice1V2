package dacd.lopez;

public interface EventStore {
    void saveEvent(String eventJson);
}
