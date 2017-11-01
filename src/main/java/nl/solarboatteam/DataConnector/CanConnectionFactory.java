package nl.solarboatteam.DataConnector;


import nl.solarboatteam.DataConnector.kafka.can.CanKafkaConnectionFactory;
import nl.solarboatteam.DataConnector.models.ConnectionMode;
import nl.solarboatteam.DataConnector.models.can.CanMessage;

import java.util.Map;

public interface CanConnectionFactory {
    /**
     * Get a consumer that listens for a specific client
     * @param connectConfig the connection config
     * @param mode the connection mode
     * @param client the client to listen to
     * @return
     */
    Consumer<CanMessage> getConsumer(Map<String, Object> connectConfig, ConnectionMode mode, String client);

    /**
     * Get a consumer that listens for all clients
     * @param connectConfig the connection config
     * @param mode the connection mode
     * @return
     */
    Consumer<CanMessage> getConsumer(Map<String, Object> connectConfig, ConnectionMode mode);

    /**
     * Get a producer that is able to send CanMessages
     * @param connectConfig the conection config
     * @param mode the connection mode
     * @return
     */
    Producer<CanMessage> getProducer(Map<String, Object> connectConfig, ConnectionMode mode);

    static CanConnectionFactory getFactory() {
        return new CanKafkaConnectionFactory();
    }

}
