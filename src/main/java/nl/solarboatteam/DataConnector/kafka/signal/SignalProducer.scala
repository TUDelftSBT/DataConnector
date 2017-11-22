package nl.solarboatteam.DataConnector.kafka.signal

import nl.solarboatteam.DataConnector.Producer
import nl.solarboatteam.DataConnector.kafka.Topic
import nl.solarboatteam.DataConnector.models.data.{Data, SignalUpdate}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

class SignalProducer(topic : Topic, producer : KafkaProducer[String, Data]) extends Producer[SignalUpdate]{

  override def send(client: String, signalUpdate: SignalUpdate): Unit = {
    val curTopic = topic.copy(client = Some(client)).copy(signal = Some(signalUpdate.getSignal))
    val newRecord = new ProducerRecord[String, Data](curTopic.get.left.get, null, signalUpdate.getData)
    producer.send(newRecord)
  }
}
