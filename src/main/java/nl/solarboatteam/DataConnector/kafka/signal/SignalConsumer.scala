package nl.solarboatteam.DataConnector.kafka.signal

import java.util.regex.Pattern

import io.reactivex.Observable
import nl.solarboatteam.DataConnector.Consumer
import nl.solarboatteam.DataConnector.kafka.{AbstractConsumer, TopicHelper}
import nl.solarboatteam.DataConnector.models.Update
import nl.solarboatteam.DataConnector.models.data.{Data, SignalUpdate}
import org.apache.kafka.clients.consumer.{KafkaConsumer => RealKafkaConsumer}


class SignalConsumer(val consumer : RealKafkaConsumer[String,Data]) extends  Consumer[SignalUpdate]{
  val innerConsumer = new AbstractConsumer[Data](consumer)

  private[kafka] def subscribe(pattern : Pattern) : Unit = {
    innerConsumer.subscribe(pattern)
  }

  private[kafka] def subscribe(topic : String) : Unit = {
    innerConsumer.subscribe(topic)
  }

  private[kafka] def subscribe(topics : List[String]) : Unit = {
    innerConsumer.subscribe(topics)
  }

  override def start(): Unit = innerConsumer.start()

  override def stop(): Unit = innerConsumer.stop()

  override def getObservable: Observable[Update[SignalUpdate]] = {
    innerConsumer.getObservable
      .map(x => new Update(TopicHelper.getClient(x.topic()), new SignalUpdate(TopicHelper.getSignal(x.topic), x.value())))
  }

}
