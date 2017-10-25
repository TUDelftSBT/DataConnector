package nl.solarboatteam.DataConnector;

import nl.solarboatteam.DataConnector.kafka.signal.SignalKafkaConnectionFactory;
import nl.solarboatteam.DataConnector.models.ConnectionMode;
import nl.solarboatteam.DataConnector.models.data.SignalUpdate;

import java.util.Map;

public interface SignalConnectionFactory {
    /**
     * Get a consumer that listens for a specific client and specific signal
     * @param connectConfig the connection config
     * @param mode the connection mode
     * @param client the client to listen for
     * @param signal the signal to listen for
     * @return
     */
    Consumer<SignalUpdate> getConsumer(Map<String, Object> connectConfig, ConnectionMode mode, String client, String signal);

    /**
     * Get a consumer that listens for a specific client and for all signals
     * @param connectConfig the connection config
     * @param mode the connection mode
     * @param client the client to listen to
     * @return
     */
    Consumer<SignalUpdate> getConsumer(Map<String, Object> connectConfig, ConnectionMode mode, String client);

    /**
     * Get a consumer that listens for all clients and for all signals
     * @param connectConfig the connection config
     * @param mode the connection mode
     * @return
     */
    Consumer<SignalUpdate> getConsumer(Map<String, Object> connectConfig, ConnectionMode mode);

    /**
     * Get a producer that is able to send SignalUpdates
     * @param connectConfig the conection config
     * @param mode the connection mode
     * @param client the client to send to
     * @return
     */
    Producer<SignalUpdate> getProducer(Map<String, Object> connectConfig, ConnectionMode mode, String client);

    static SignalConnectionFactory getFactory() {
        return new SignalKafkaConnectionFactory();
    }
}
