package nl.solarboatteam.DataConnector.kafka

import java.time.Instant
import java.util

import io.reactivex.Observable
import nl.solarboatteam.DataConnector.DataConnector
import nl.solarboatteam.DataConnector.kafka.serialization.{JsonDeserializer, JsonSerializer}
import nl.solarboatteam.DataConnector.models.data.{Data, SignalUpdate}
import play.api.libs.json.{Format, __}
import play.api.libs.functional.syntax._


import scala.collection.JavaConverters._

class KafkaDataConnector(private val connectConfig : util.Map[String, Object], private val client : String) extends DataConnector {

  // play json scala magic :)
  // used by the (de)serializer
  implicit val dataFormat: Format[Data] = (
    (__ \ "timestamp").format[Instant]
      and (__ \ "value").format[Double]

    )((timestamp: Instant, value: Double)=> new Data(timestamp, value), data => (data.getTimestamp,data.getValue))

  private val kafkaConnector = new KafkaConnector[Data](connectConfig, client, new JsonSerializer[Data](), new JsonDeserializer[Data]())
  override def start(): Unit = kafkaConnector.start()

  override def getObservable(signals: util.List[String]): Observable[SignalUpdate] = {
    val signalsToListenTo = signals.asScala.toSet
    getObservable
      .filter(x => signalsToListenTo.contains(x.signal))

  }

  override def getObservable: Observable[SignalUpdate] = {
    kafkaConnector.getObservable
      .map(rec => new SignalUpdate(rec.key, rec.value()))
  }

  override def send(signalUpdate: SignalUpdate): Unit = kafkaConnector.send(signalUpdate.signal, signalUpdate.data)

  override def stop(): Unit = kafkaConnector.stop()
}
