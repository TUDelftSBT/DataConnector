package nl.solarboatteam.DataConnector;

import nl.solarboatteam.DataConnector.kafka.signal.SignalKafkaConnectionFactory;
import nl.solarboatteam.DataConnector.models.ConnectionMode;
import nl.solarboatteam.DataConnector.models.data.SignalUpdate;

import java.util.List;
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
     * Get a consumer that listens for a specific client and a list of signals
     * @param connectConfig the connection config
     * @param mode the connection mode
     * @param client the client to listen for
     * @param signal the signals to listen for
     * @return
     */
    Consumer<SignalUpdate> getConsumer(Map<String, Object> connectConfig, ConnectionMode mode, String client, List<String> signal);

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
     * @param connectConfig the connection config
     * @param mode the connection mode
     * @return
     */
    Producer<SignalUpdate> getProducer(Map<String, Object> connectConfig, ConnectionMode mode);

    static SignalConnectionFactory getFactory() {
        return new SignalKafkaConnectionFactory();
    }
}
