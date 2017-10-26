package nl.solarboatteam.DataConnector.kafka.can

import nl.solarboatteam.DataConnector.Consumer
import nl.solarboatteam.DataConnector.kafka.{AbstractConsumer, Topic}
import nl.solarboatteam.DataConnector.models.Update
import nl.solarboatteam.DataConnector.models.can.CanMessage
import org.apache.kafka.clients.consumer.{KafkaConsumer => RealKafkaConsumer}
import rx.Observable

class CanConsumer(val consumer : RealKafkaConsumer[String,CanMessage]) extends Consumer[CanMessage]{
  val innerConsumer = new AbstractConsumer[CanMessage](consumer)

  private[kafka] def subscribe(topic : Topic) : Unit = {
    val either = topic.get
    either match {
      case Left(x) => innerConsumer.subscribe(x)
      case Right(x) => innerConsumer.subscribe(x)
    }
  }

  override def start(): Unit = innerConsumer.start()

  override def stop(): Unit = innerConsumer.stop()

  override def getObservable: Observable[Update[CanMessage]] = {
    innerConsumer.getObservable.map(x => new Update(Topic.fromString(x.topic()).client.get, x.value()))
  }

}
