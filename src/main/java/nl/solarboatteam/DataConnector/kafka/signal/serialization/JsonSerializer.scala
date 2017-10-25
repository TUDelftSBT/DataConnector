package nl.solarboatteam.DataConnector.kafka.signal.serialization

import java.util

import org.apache.kafka.common.serialization.{Serializer, StringSerializer}
import play.api.libs.json.{Format, Json}

class JsonSerializer[K](implicit format : Format[K]) extends Serializer[K] {
  private val stringSerializer = new StringSerializer()

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {
    stringSerializer.configure(configs, isKey)
  }

  override def serialize(topic: String, data: K): Array[Byte] = {
    stringSerializer.serialize(topic, Json.stringify(Json.toJson[K](data)))
  }

  override def close(): Unit = {
    stringSerializer.close()
  }
}