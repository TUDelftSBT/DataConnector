package nl.solarboatteam.DataConnector.models.data;

import java.time.Instant;

public class Data {
    private final Instant timestamp;
    private final double value;

    public Data(Instant timestamp, double value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Data{" +
                "timestamp=" + timestamp +
                ", value=" + value +
                '}';
    }
}
