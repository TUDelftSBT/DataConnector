package nl.solarboatteam.DataConnector.kafka.can

import nl.solarboatteam.DataConnector.Producer
import nl.solarboatteam.DataConnector.kafka.Topic
import nl.solarboatteam.DataConnector.models.can.CanMessage
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

class CanProducer(toTopic : Topic, producer : KafkaProducer[String, CanMessage]) extends Producer[CanMessage]{

  override def send(client : String, msg: CanMessage): Unit = {
    val curTopic = toTopic.copy(client = Some(client))
    val newRecord = new ProducerRecord[String, CanMessage](curTopic.get.left.get, null, msg)
    producer.send(newRecord)
  }
}
