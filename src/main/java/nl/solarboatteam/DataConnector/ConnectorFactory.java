package nl.solarboatteam.DataConnector;

import nl.solarboatteam.DataConnector.kafka.KafkaCanConnector;
import nl.solarboatteam.DataConnector.kafka.KafkaDataConnector;

import java.util.Map;

public class ConnectorFactory {
    public static CanConnector createCanConnector(Map<String, Object> config, String client) {
        return new KafkaCanConnector(config, client);
    }

    public static DataConnector createDataConnector(Map<String, Object> config, String client) {
        return new KafkaDataConnector(config, client);
    }
}
