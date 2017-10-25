package nl.solarboatteam.DataConnector.kafka.can

import java.util.regex.Pattern

import io.reactivex.Observable
import nl.solarboatteam.DataConnector.Consumer
import nl.solarboatteam.DataConnector.kafka.{AbstractConsumer, TopicHelper}
import nl.solarboatteam.DataConnector.models.Update
import nl.solarboatteam.DataConnector.models.can.CanMessage
import org.apache.kafka.clients.consumer.{KafkaConsumer => RealKafkaConsumer}

class CanConsumer(val consumer : RealKafkaConsumer[String,CanMessage]) extends Consumer[CanMessage]{
  val innerConsumer = new AbstractConsumer[CanMessage](consumer)

  private[kafka] def subscribe(topic: String) : Unit = innerConsumer.subscribe(topic)
  private[kafka] def subscribe(topic: Pattern) : Unit = innerConsumer.subscribe(topic)


  override def start(): Unit = innerConsumer.start()

  override def stop(): Unit = innerConsumer.stop()

  override def getObservable: Observable[Update[CanMessage]] = {
    innerConsumer.getObservable.map(x => new Update(TopicHelper.getClient(x.topic()), x.value()))
  }

}
