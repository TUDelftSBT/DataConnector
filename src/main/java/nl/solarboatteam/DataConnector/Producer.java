package nl.solarboatteam.DataConnector;

public interface Producer<T> {

    /**
     * Send a T to a client
     * @param msg the message to send
     */
    void send(T msg);
}
