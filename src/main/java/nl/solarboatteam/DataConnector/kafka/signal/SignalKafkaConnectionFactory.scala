package nl.solarboatteam.DataConnector.kafka.signal

import java.time.Instant
import java.util

import nl.solarboatteam.DataConnector.kafka.{MessageType, Topic}
import nl.solarboatteam.DataConnector.kafka.signal.serialization.{JsonDeserializer, JsonSerializer}
import nl.solarboatteam.DataConnector.models.ConnectionMode
import nl.solarboatteam.DataConnector.models.data.{Data, SignalUpdate}
import nl.solarboatteam.DataConnector.{Consumer, Producer, SignalConnectionFactory}
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import play.api.libs.functional.syntax._
import play.api.libs.json.{Format, __}

import scala.collection.JavaConverters._
import scala.collection.mutable

class SignalKafkaConnectionFactory extends SignalConnectionFactory {

  // play json scala magic :)
  // used by the (de)serializer
  implicit val dataFormat: Format[Data] = (
    (__ \ "timestamp").format[Instant]
      and (__ \ "value").format[Double]

    )((timestamp: Instant, value: Double)=> new Data(timestamp, value), data => (data.getTimestamp,data.getValue))

  private val serializer = new JsonSerializer[Data]()
  private val deserializer = new JsonDeserializer[Data]()
  private val groupIds = new mutable.HashSet[String]()


  private def getConsumer(connectConfig: util.Map[String, Object]) : SignalConsumer = {
    val groupId = connectConfig.get("group.id")
    if(groupId == null || !groupId.isInstanceOf[String]) {
      throw new RuntimeException("You have not submitted a valid group.id field.")
    }
    val groupIdString = groupId.asInstanceOf[String]

    if(groupIds.contains(groupIdString)){
      throw new RuntimeException("You have already used this group.id. Please reuse the observable or use a new group.id.")
    }
    groupIds.add(groupIdString)

    val kafkaConsumer = new KafkaConsumer[String, Data](connectConfig,new StringDeserializer(), deserializer)
    val consumer = new SignalConsumer(kafkaConsumer)
    consumer
  }

  def getConsumer(connectConfig: util.Map[String, Object], mode:ConnectionMode, client: String, signal: String): Consumer[SignalUpdate] = {
    val consumer = getConsumer(connectConfig)
    consumer.subscribe(Topic(mode, MessageType.JSON, Some(client), Some(signal)))
    consumer
  }

  override def getConsumer(connectConfig: util.Map[String, AnyRef], mode: ConnectionMode, client: String, signals: util.List[String]): Consumer[SignalUpdate] = {
    val consumer = getConsumer(connectConfig)
    consumer.subscribe(signals.asScala.toList.map(sig => Topic(mode, MessageType.JSON, Some(client), Some(sig))))
    consumer
  }


  def getConsumer(connectConfig: util.Map[String, Object], mode:ConnectionMode, client: String): Consumer[SignalUpdate] = {
    val consumer = getConsumer(connectConfig)
    val topic = Topic(mode,MessageType.JSON, Some(client))
    consumer.subscribe(topic)
    consumer
  }

  def getConsumer(connectConfig: util.Map[String, Object], mode:ConnectionMode): Consumer[SignalUpdate] = {
    val consumer = getConsumer(connectConfig)
    val topic = Topic(mode, MessageType.JSON)
    consumer.subscribe(topic)
    consumer
  }

  def getProducer(connectConfig: util.Map[String, Object], mode:ConnectionMode): Producer[SignalUpdate] = {
    val topic = Topic(mode, msgType = MessageType.JSON)
    val kafkaProducer = new KafkaProducer[String, Data](connectConfig,new StringSerializer(), serializer)
    new SignalProducer(topic, kafkaProducer)
  }
}
