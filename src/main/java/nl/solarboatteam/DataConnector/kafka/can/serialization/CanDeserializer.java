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
        if(data == null) {
            return null;
        }
        ByteBuffer buffer = ByteBuffer.wrap(data);

        char dataType = (char) buffer.get();
        if(dataType != 'D') {
            //this is not a CanDataFrame, so let's ignore it.
            return null;
        }

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
