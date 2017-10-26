package nl.solarboatteam.DataConnector.kafka.can.serialization;


import nl.solarboatteam.DataConnector.models.can.CanMessage;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.Map;

public class CanDeserializer implements Deserializer<CanMessage> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public CanMessage deserialize(String topic, byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);

        long ms = buffer.getLong();
        Instant time = Instant.ofEpochMilli(ms);
        int id = buffer.getInt() & 0x7fffffff;

        byte[] innerData = new byte[9];
        buffer.get(innerData);
        return new CanMessage(id, innerData, time);
    }

    @Override
    public void close() {
    }
}
