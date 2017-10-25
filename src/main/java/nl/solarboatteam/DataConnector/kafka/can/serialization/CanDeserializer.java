package nl.solarboatteam.DataConnector.kafka.can.serialization;


import nl.solarboatteam.DataConnector.models.can.CanMessage;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class CanDeserializer implements Deserializer<CanMessage> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public CanMessage deserialize(String topic, byte[] data) {
        //todo convert byte to CanMessage
        return null;
    }

    @Override
    public void close() {
    }
}
