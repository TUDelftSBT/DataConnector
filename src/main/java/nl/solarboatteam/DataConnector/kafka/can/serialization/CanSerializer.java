package nl.solarboatteam.DataConnector.kafka.can.serialization;

import nl.solarboatteam.DataConnector.models.can.CanMessage;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;
import java.util.Map;

public class CanSerializer implements Serializer<CanMessage> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, CanMessage data) {
        ByteBuffer buffer = ByteBuffer.allocate(22);
        // D means CanDataFrame
        buffer.put((byte)'D');
        buffer.putLong(data.getTimestamp().toEpochMilli());
        buffer.putInt(data.getId()|0x80000000);
        buffer.put(data.getData());
        return buffer.array();
    }

    @Override
    public void close() {

    }
}