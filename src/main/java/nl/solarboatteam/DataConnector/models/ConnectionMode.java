package nl.solarboatteam.DataConnector.models;

public enum ConnectionMode {
    /**
     * specifies that you want to receive data that is sent by the client
     */
    RECEIVE_DATA_FROM_CLIENT,
    /**
     * specifies that you want to receive data that is sent to the client
     */
    RECEIVE_DATA_TO_CLIENT
}
