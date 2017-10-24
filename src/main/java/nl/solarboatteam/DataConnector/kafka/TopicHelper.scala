package nl.solarboatteam.DataConnector.kafka

object TopicHelper {
  def getFromCanTopic(client: String) = {
    "from/"+client+"/CAN/"
  }

  def getToCanTopic(client: String) = {
    "to/"+client+"/CAN/"
  }


  def getFromJsonTopic(client: String) : String = {
    "from/"+client+"/JSON/"
  }

  def getToJsonTopic(client: String) : String = {
    "to/"+client+"/JSON/"
  }


}
