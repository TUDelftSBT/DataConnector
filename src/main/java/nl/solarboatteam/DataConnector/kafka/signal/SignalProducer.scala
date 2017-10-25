package nl.solarboatteam.DataConnector.kafka.signal

import nl.solarboatteam.DataConnector.Producer
import nl.solarboatteam.DataConnector.models.data.{Data, SignalUpdate}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

class SignalProducer(topicPrefix : String, producer : KafkaProducer[String, Data]) extends Producer[SignalUpdate]{

  override def send(signalUpdate: SignalUpdate): Unit = {
    val newRecord = new ProducerRecord[String, Data](topicPrefix+signalUpdate.getSignal, null, signalUpdate.getData)
    producer.send(newRecord)
  }
}
