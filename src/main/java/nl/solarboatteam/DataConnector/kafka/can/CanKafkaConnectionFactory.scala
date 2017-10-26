package nl.solarboatteam.DataConnector.kafka.can

import java.util

import nl.solarboatteam.DataConnector.kafka.can.serialization.{CanDeserializer, CanSerializer}
import nl.solarboatteam.DataConnector.kafka.{MessageType, Topic}
import nl.solarboatteam.DataConnector.models.ConnectionMode
import nl.solarboatteam.DataConnector.models.can.CanMessage
import nl.solarboatteam.DataConnector.{CanConnectionFactory, Consumer, Producer}
import org.apache.kafka.clients.consumer.{KafkaConsumer => RealKafkaConsumer}
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}

import scala.collection.mutable


class CanKafkaConnectionFactory extends CanConnectionFactory{
  private val serializer = new CanSerializer()
  private val deserializer = new CanDeserializer()
  private val groupIds = new mutable.HashSet[String]()

  private def getConsumer(connectConfig: util.Map[String, Object]): CanConsumer = {
    val groupId = connectConfig.get("group.id")
    if(groupId == null || !groupId.isInstanceOf[String]) {
      throw new RuntimeException("You have not submitted a valid group.id field.")
    }
    val groupIdString = groupId.asInstanceOf[String]

    if(groupIds.contains(groupIdString)){
      throw new RuntimeException("You have already used this group.id. Please reuse the observable or use a new group.id.")
    }
    groupIds.add(groupIdString)

    val kafkaConsumer = new RealKafkaConsumer[String, CanMessage](connectConfig, new StringDeserializer(), deserializer)
    new CanConsumer(kafkaConsumer)
  }

  def getConsumer(connectConfig: util.Map[String, Object], mode:ConnectionMode, client: String): Consumer[CanMessage] = {
    val consumer = getConsumer(connectConfig)
    consumer.subscribe(Topic(mode, MessageType.CAN, Some(client)))
    consumer
  }

  def getConsumer(connectConfig: util.Map[String, Object], mode:ConnectionMode): Consumer[CanMessage] = {
    val consumer = getConsumer(connectConfig)
    consumer.subscribe(Topic(mode, MessageType.CAN))
    consumer
  }

  def getProducer(connectConfig: util.Map[String, Object], mode : ConnectionMode): Producer[CanMessage] = {
    val kafkaProducer = new KafkaProducer[String, CanMessage](connectConfig, new StringSerializer(), serializer)
    val topic = Topic(mode, MessageType.CAN)
    new CanProducer(topic, kafkaProducer)
  }
}
