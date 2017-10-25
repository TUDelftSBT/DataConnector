package nl.solarboatteam.DataConnector.kafka

import java.util.regex.Pattern

import nl.solarboatteam.DataConnector.models.ConnectionMode

object TopicHelper {

  private val separator = "."
  def getModeStr(mode : ConnectionMode): String = if (mode == ConnectionMode.FROM_CLIENT) "from" else "to"


  def getJsonTopic(mode :ConnectionMode, client: String, signal: String): String = {
    val modeStr = getModeStr(mode)
    List(modeStr,client,"JSON",signal).mkString(separator)
  }

  def getJsonTopic(mode :ConnectionMode, client: String) : Pattern = {
    val modeStr = getModeStr(mode)
    Pattern.compile(List(modeStr,client,"JSON",".+").mkString("\\"+separator))
  }

  def getJsonTopic(mode :ConnectionMode): Pattern = {
    val modeStr = getModeStr(mode)
    Pattern.compile(List(modeStr,".+","JSON",".+").mkString("\\"+separator))
  }

  def getJsonTopics(mode :ConnectionMode, client: String, signals : List[String]): List[String] = {
    val modeStr = getModeStr(mode)
    signals.map(sig => List(modeStr,client,"JSON",sig).mkString(separator))
  }


  def getCanTopic(mode :ConnectionMode, client: String) : String = {
    val modeStr = getModeStr(mode)
    List(modeStr,client,"CAN").mkString(separator)
  }

  def getCanTopic(mode :ConnectionMode): Pattern = {
    val modeStr = getModeStr(mode)
    Pattern.compile(List(modeStr,".+","CAN").mkString("\\"+separator))
  }

  def getSignal(topic: String) : String = {
    topic.split("\\"+separator)(3)
  }

  def getClient(topic: String) : String = {
    topic.split("\\"+separator)(1)
  }
}
