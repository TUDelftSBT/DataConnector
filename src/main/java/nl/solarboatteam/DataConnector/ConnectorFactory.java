package nl.solarboatteam.DataConnector;

import nl.solarboatteam.DataConnector.kafka.KafkaCanConnector;
import nl.solarboatteam.DataConnector.kafka.KafkaDataConnector;

import java.util.Map;

public class ConnectorFactory {
    /**
     * Create a CanConnector
     * @param config the connection config
     * @param client the client to listen to e.g. boat_2017
     * @return
     */
    public static CanConnector createCanConnector(Map<String, Object> config, String client) {
        return new KafkaCanConnector(config, client);
    }

    /**
     * Create a DataConnector
     * @param config the connection config
     * @param client the client to listen to e.g. boat_2017
     * @return
     */
    public static DataConnector createDataConnector(Map<String, Object> config, String client) {
        return new KafkaDataConnector(config, client);
    }
}
