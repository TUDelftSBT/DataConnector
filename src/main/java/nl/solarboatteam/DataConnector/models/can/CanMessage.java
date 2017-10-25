package nl.solarboatteam.DataConnector.models.can;

public class CanMessage {
    //todo implement
    private final int id;

    public CanMessage(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CanMessage{}";
    }

    public int getId() {
        return id;
    }
}
