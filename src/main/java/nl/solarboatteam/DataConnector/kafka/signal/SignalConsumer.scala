package nl.solarboatteam.DataConnector.kafka.signal


import nl.solarboatteam.DataConnector.Consumer
import nl.solarboatteam.DataConnector.kafka.{AbstractConsumer, Topic}
import nl.solarboatteam.DataConnector.models.Update
import nl.solarboatteam.DataConnector.models.data.{Data, SignalUpdate}
import org.apache.kafka.clients.consumer.{KafkaConsumer => RealKafkaConsumer}
import rx.Observable


class SignalConsumer(val consumer : RealKafkaConsumer[String,Data]) extends  Consumer[SignalUpdate]{
  val innerConsumer = new AbstractConsumer[Data](consumer)

  private[kafka] def subscribe(topic : Topic) : Unit = {
    val either = topic.get
    either match {
      case Left(x) => innerConsumer.subscribe(x)
      case Right(x) => innerConsumer.subscribe(x)
    }
  }

  private[kafka] def subscribe(topics : List[Topic]) : Unit = {
    innerConsumer.subscribe(topics.map(_.get.left.get))
  }



    override def start(): Unit = innerConsumer.start()

  override def stop(): Unit = innerConsumer.stop()

  override def getObservable: Observable[Update[SignalUpdate]] = {
    innerConsumer.getObservable
      .map(x => new Update(Topic.fromString(x.topic).client.get, new SignalUpdate(Topic.fromString(x.topic).signal.get, x.value())))
  }

}
