package nl.solarboatteam.DataConnector.kafka

import nl.solarboatteam.DataConnector.models.ConnectionMode
import org.scalatest.FlatSpec

class TopicTest extends FlatSpec  {
  "Topic Pattern with two wildcards" should " match from.boat_2016.JSON.gps" in {
    val testPattern = Topic(ConnectionMode.FROM_CLIENT, MessageType.JSON, None, None).get.right.get
    assert(testPattern.asPredicate().test("from.boat_2016.JSON.gps"))
  }

  "Topic Pattern with two wildcards" should " should not match from-boat_2016.JSON.gps" in {
    val testPattern = Topic(ConnectionMode.FROM_CLIENT, MessageType.JSON, None, None).get.right.get
    assert(!testPattern.asPredicate().test("from-boat_2016.JSON.gps"))
  }

  "Topic to.boat_2017.CAN " should " should result in the right topic when using fromString" in {
    val topic = Topic.fromString("to.boat_2017.CAN")
    assert(topic == Topic(ConnectionMode.TO_CLIENT, MessageType.CAN, Some("boat_2017")))
  }

  "Topic from.boat_2016.JSON.power " should " should result in the right topic when using fromString" in {
    val topic = Topic.fromString("from.boat_2016.JSON.power")
    assert(topic == Topic(ConnectionMode.FROM_CLIENT, MessageType.JSON, Some("boat_2016"),Some("power")))
  }

  "Topic.get " should " should result in the right String: from.boat_2014.JSON.gps" in {
    val topic = Topic(ConnectionMode.FROM_CLIENT, MessageType.JSON, Some("boat_2014"), Some("gps"))
    assert(topic.get.left.get == "from.boat_2014.JSON.gps")
  }

}
