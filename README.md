# Usage

```java
import nl.solarboatteam.DataConnector.ConnectorFactory;
import java.utils.ArrayList();

DataConnector connector = ConnectorFactory.createDataConnector(config, "boat_2016");
List signals = new ArrayList<String>();
signals.add("gps");
Observable<SignalUpdate> obs = connector.getObservable(signals);
Subscription subscription = obs.subscribe(update -> System.out.println("got "+update.signal+" timestamp"+update.data.timestamp+" value"+update.data.value));
//stop listening
subscription.unsubscribe();
//stop everything
connector.stop()

```
