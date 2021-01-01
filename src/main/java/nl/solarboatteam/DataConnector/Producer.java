package nl.solarboatteam.DataConnector;

public interface Producer<T> {

    /**
     * Send a T to a client
     * @param client the client to send to or from
     * @param msg the message to send
     */
    void send(String client, T msg);
}
