package nl.solarboatteam.DataConnector.models;

public enum ConnectionMode {
    /**
     * specifies that you want to receive or send data that is sent FROM the client
     * e.g. from boat 2017
     */
    FROM_CLIENT,
    /**
     * specifies that you want to receive or send data that is sent TO the client
     * e.g. to boat 2017
     */
    TO_CLIENT
}
