[![Release](https://jitpack.io/v/TUDelftSBT/DataConnector.svg)](https://jitpack.io/#TUDelftSBT/DataConnector)

# Usage

```java
import nl.solarboatteam.DataConnector.ConnectorFactory;
import java.utils.ArrayList;
import java.util.HashMap;
import java.util.Map;

Map<String,Object> config = new HashMap<String,Object>();
config.put("bootstrap.servers", "localhost:9092");
config.put("group.id", "application-name");

SignalConnectionFactory factory = SignalConnectionFactory.getFactory();
Producer<SignalUpdate> producer = factory.getProducer(config, Connection_Mode.FROM_CLIENT, "boat_2016");
//send GPS message as if it was send from "boat_2016"
producer.send(new SignalUpdate("gps", new Data(Instant.now(), 0.99999)));

//listen to GPS messages from boat_2016
Consumer<SignalUpdate> consumer = factory.getConsumer(config, Connection_Mode.FROM_CLIENT, "boat_2016", "gps");
Observable<Update<SignalUpdate>> obs = consumer.getObservable();
Subscription subscription = obs.subscribe(System::out::println);
//do this after subscribing, else you could miss data.
consumer.start();

//stop listening
subscription.unsubscribe();
//stop the consumer
consumer.stop()

```
