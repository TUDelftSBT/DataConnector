package nl.solarboatteam.DataConnector.kafka.can.serialization;


import nl.solarboatteam.DataConnector.models.can.CanMessage;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CanDeserializer implements Deserializer<CanMessage> {
    private static Logger LOG  = LoggerFactory.getLogger(CanDeserializer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public CanMessage deserialize(String topic, byte[] data) {
        if(data == null) {
            return null;
        }
        ByteBuffer buffer = ByteBuffer.wrap(data);

        try {
            char dataType = (char) buffer.get();
            if (dataType != 'D') {
                //this is not a CanDataFrame, so let's ignore it.
                return null;
            }

            long ms = buffer.getLong();
            Instant time = Instant.ofEpochMilli(ms);
            int id = buffer.getInt() & 0x7fffffff;
            //ignore the DLC
            buffer.get();
            byte[] innerData = new byte[8];
            buffer.get(innerData);
            return new CanMessage(id, innerData, time);
        }
        catch(BufferUnderflowException | DateTimeException e) {
            LOG.warn("Could not deserialize message "+ Arrays.toString(data)+" from topic "+topic, e);
            return null;
        }
    }

    @Override
    public void close() {
    }
}
