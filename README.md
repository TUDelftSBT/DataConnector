# Usage

```java
import nl.solarboatteam.DataConnector.ConnectorFactory;
import java.utils.ArrayList;
import java.util.HashMap;
import java.util.Map;

Map<String,Object> config = new HashMap<String,Object>();
config.put("bootstrap.servers", "localhost:9092");
config.put("group.id", "application-name");

DataConnector connector = ConnectorFactory.createDataConnector(config, "boat_2016");
//send test message
connector.send(new SignalUpdate("test", new Data(Instant.now(), 0.99999)));

//get gps updates
List signals = new ArrayList<String>();
signals.add("gps");

Observable<SignalUpdate> obs = connector.getObservable(signals);
Subscription subscription = obs.subscribe(update -> System.out.println("got "+update.signal+" timestamp"+update.data.timestamp+" value"+update.data.value));

//stop listening
subscription.unsubscribe();
//stop everything
connector.stop()

```
