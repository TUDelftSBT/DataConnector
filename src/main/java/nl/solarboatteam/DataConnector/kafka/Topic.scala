package nl.solarboatteam.DataConnector.kafka

import java.util.regex.Pattern

import nl.solarboatteam.DataConnector.kafka.MessageType.MessageType
import nl.solarboatteam.DataConnector.models.ConnectionMode

object MessageType extends Enumeration {
  type MessageType = Value
  val JSON, CAN = Value
}

case class Topic(mode : ConnectionMode, msgType: MessageType, client : Option[String] = None, signal : Option[String] = None) {
  private def getModeStr(mode : ConnectionMode): String = if (mode == ConnectionMode.FROM_CLIENT) "from" else "to"


  override def toString: String = {
    List(getModeStr(mode),client,msgType.toString,signal).mkString(Topic.separator)
  }

  /**
    *
    * @return a string when there are no wildcards. Else return a Pattern
    */
  def get : Either[String, Pattern] = {
    val list =
      if(msgType == MessageType.CAN)
        List(Some(getModeStr(mode)),client,Some(msgType.toString))
      else
        List(Some(getModeStr(mode)),client,Some(msgType.toString), signal)

      if(list.contains(None))
        Right(Pattern.compile(list.map(_.getOrElse(".*")).mkString(Topic.separator)))
      else
        Left(list.map(_.get).mkString(Topic.separator))
  }
}

object Topic {
  private val separator = "."
  private def getMode(mode : String): ConnectionMode = if (mode == "from") ConnectionMode.FROM_CLIENT else ConnectionMode.TO_CLIENT

  def fromString(topic : String) : Topic = {
    val splitted = topic.split("//"+separator).lift

    Topic(getMode(splitted(0).get), MessageType.withName(splitted(2).get), splitted(1), splitted(3))
  }
}
