package nl.solarboatteam.DataConnector.models.data;

public class SignalUpdate {


    private final String signal;
    private final Data data;

    public SignalUpdate(String signal, Data data) {
        this.signal = signal;
        this.data = data;
    }

    public String getSignal() {
        return signal;
    }

    public Data getData() {
        return data;
    }

    @Override
    public String toString() {
        return "SignalUpdate{" +
                "signal='" + signal + '\'' +
                ", data=" + data +
                '}';
    }
}
