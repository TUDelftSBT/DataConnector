[![Release](https://jitpack.io/v/TUDelftSBT/DataConnector.svg)](https://jitpack.io/#TUDelftSBT/DataConnector)
# About this library
This library allows applications to receive and send CAN messages or signal updates from and to boats (or other client) using [Apache Kafka](https://kafka.apache.org).
The interface this library exposes is intentionally Kafka agnostic.
This makes it possible to switch to another message broker without breaking client applications.
It is therefore important to only use the `Consumer`, `Producer`, `CanConnectionFactory` and `SignalConnectionFactory`
interfaces instead of the Kafka specific implementations.

# Installation

## Maven
add 
```
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
and
```
<dependency>
	    <groupId>com.github.TUDelftSBT</groupId>
	    <artifactId>DataConnector</artifactId>
	    <version>master-SNAPSHOT</version>
	</dependency>
```
to your buildfile.

## SBT
add to your build.sbt file:
```
    resolvers += "jitpack" at "https://jitpack.io"
```
and
```
    libraryDependencies += "com.github.TUDelftSBT" % "DataConnector" % "master-SNAPSHOT"

```
# Example

```java
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
