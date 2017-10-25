package nl.solarboatteam.DataConnector.kafka.can

import nl.solarboatteam.DataConnector.Producer
import nl.solarboatteam.DataConnector.models.can.CanMessage
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

class CanProducer(toTopic : String, producer : KafkaProducer[String, CanMessage]) extends Producer[CanMessage]{

  override def send(msg: CanMessage): Unit = {
    val newRecord = new ProducerRecord[String, CanMessage](toTopic, null, msg)
    producer.send(newRecord)
  }
}
