package nl.solarboatteam.DataConnector.models;

public class Update<T> {
    private final String client;
    private final T data;

    public Update(String client, T data) {
        this.client = client;
        this.data = data;
    }

    public String getClient() {
        return client;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Update{" +
                "client='" + client + '\'' +
                ", data=" + data +
                '}';
    }
}
