package nl.solarboatteam.DataConnector.kafka.can.serialization;

import nl.solarboatteam.DataConnector.models.can.CanMessage;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class CanSerializer implements Serializer<CanMessage> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, CanMessage data) {
        //CanMessage to bytes
        return new byte[0];
    }

    @Override
    public void close() {

    }
}