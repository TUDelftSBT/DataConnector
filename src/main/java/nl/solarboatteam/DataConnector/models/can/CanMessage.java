package nl.solarboatteam.DataConnector.models.can;

import java.time.Instant;

public class CanMessage {
    private final int id;
    private final byte[] data;
    private final Instant timestamp;

    public CanMessage(int id, byte[] data, Instant timestamp) {
        this.id = id;
        this.data = data;
        this.timestamp = timestamp;
    }


    public int getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
