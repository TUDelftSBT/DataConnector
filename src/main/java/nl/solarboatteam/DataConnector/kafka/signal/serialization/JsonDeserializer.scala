package nl.solarboatteam.DataConnector.kafka.signal.serialization

import java.util

import org.apache.kafka.common.serialization.{Deserializer, StringDeserializer}
import play.api.libs.json.{Format, Json}

class JsonDeserializer[K >: Null](implicit format : Format[K]) extends Deserializer[K] {
  private val stringDeserializer = new StringDeserializer()

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {
    stringDeserializer.configure(configs, isKey)
  }

  override def close(): Unit = {
    stringDeserializer.close()
  }

  override def deserialize(topic: String, data: Array[Byte]): K = {
    val str = stringDeserializer.deserialize(topic, data)
    if(str == null) {
      return null
    }
    try {
      Json.fromJson[K](Json.parse(str)).get
    }
    catch {
     case e => return null
   }
  }
}
