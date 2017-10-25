package nl.solarboatteam.DataConnector;

import io.reactivex.Observable;
import nl.solarboatteam.DataConnector.models.Update;

public interface Consumer<V> {

    /**
     * Subscribe to a all signals from this client
     */
    Observable<Update<V>> getObservable();


    void start();
    void stop();
}
