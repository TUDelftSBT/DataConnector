package nl.solarboatteam.DataConnector.kafka

object TopicHelper {



  def topicToSender(topic : String) : String = {
    topic.split("/")(1)
  }

  def topicToSignalName(topic : String) : String = {
    topic.split("/")(3)
  }

  def getFromJsonTopic(senderName: String) : String = {
    "from/"+senderName+"/JSON/"
  }

  def getToJsonTopic(senderName: String) : String = {
    "to/"+senderName+"/JSON/"
  }


}
