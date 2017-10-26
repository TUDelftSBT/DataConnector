package nl.solarboatteam.DataConnector;

import nl.solarboatteam.DataConnector.models.Update;
import rx.Observable;

public interface Consumer<V> {

    /**
     * Subscribe to a all signals from this client
     */
    Observable<Update<V>> getObservable();


    void start();
    void stop();
}
