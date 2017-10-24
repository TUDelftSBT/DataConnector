package nl.solarboatteam.DataConnector.models.data;

public class SignalUpdate {
    public final String signal;
    public final Data data;

    public SignalUpdate(String signal, Data data) {
        this.signal = signal;
        this.data = data;
    }

    @Override
    public String toString() {
        return "SignalUpdate{" +
                "signal='" + signal + '\'' +
                ", data=" + data +
                '}';
    }
}
