package nl.solarboatteam.DataConnector;

import io.reactivex.Observable;
import nl.solarboatteam.DataConnector.models.can.CanMessage;
import nl.solarboatteam.DataConnector.models.data.SignalUpdate;

import java.util.List;

public interface CanConnector {

    void start();


    /**
     * Subscribe to a all can signals from this client
     */
    Observable<CanMessage> getObservable();

    /**
     * Send a CanMessage to a client
     * @param canMessage the CanMessage to send
     */
    void send(CanMessage canMessage);
    void stop();
}
