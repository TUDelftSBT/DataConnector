package nl.solarboatteam.DataConnector.kafka

import java.util

import io.reactivex.Observable
import nl.solarboatteam.DataConnector.models.can.CanMessage
import nl.solarboatteam.DataConnector.CanConnector
import nl.solarboatteam.DataConnector.kafka.serialization.{CanDeserializer, CanSerializer}


class KafkaCanConnector(private val connectConfig : util.Map[String, Object], private val client : String) extends CanConnector{
  private val kafkaConnector = new KafkaConnector[CanMessage](connectConfig, client, new CanSerializer(), new CanDeserializer())
  override def start(): Unit = kafkaConnector.start()


  override def getObservable: Observable[CanMessage] = {
    kafkaConnector.getObservable.map(x => x.value())
  }

  override def send(canMessage: CanMessage): Unit = kafkaConnector.send(null, canMessage)

  override def stop(): Unit = kafkaConnector.stop()
}
