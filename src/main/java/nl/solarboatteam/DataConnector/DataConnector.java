package nl.solarboatteam.DataConnector;

import io.reactivex.Observable;
import nl.solarboatteam.DataConnector.models.data.SignalUpdate;

import java.util.List;

public interface DataConnector {

    void start();

    /**
     * Subscribe to a specific signal from a client
     * @param signals the signals to listen to
     */
    Observable<SignalUpdate> getObservable(List<String> signals);

    /**
     * Subscribe to a all signals from this client
     */
    Observable<SignalUpdate> getObservable();

    /**
     * Send a SignalUpdate to a client
     * @param signalUpdate
     */
    void send(SignalUpdate signalUpdate);
    void stop();
}
