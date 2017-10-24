package nl.solarboatteam.DataConnector;

import nl.solarboatteam.DataConnector.kafka.KafkaCanConnector;
import nl.solarboatteam.DataConnector.kafka.KafkaDataConnector;
import nl.solarboatteam.DataConnector.models.ConnectionMode;

import java.util.Map;



public class ConnectorFactory {
    /**
     * Create a CanConnector
     * @param config the connection config
     * @param client the client to listen to e.g. boat_2017
     * @return
     */
    public static CanConnector createCanConnector(Map<String, Object> config, String client, ConnectionMode mode) {
        return new KafkaCanConnector(config, client, mode);
    }

    /**
     * Create a DataConnector
     * @param config the connection config
     * @param client the client to listen to e.g. boat_2017
     * @return
     */
    public static DataConnector createDataConnector(Map<String, Object> config, String client, ConnectionMode mode) {
        return new KafkaDataConnector(config, client, mode);
    }
}
