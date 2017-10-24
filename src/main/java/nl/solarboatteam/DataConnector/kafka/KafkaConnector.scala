package nl.solarboatteam.DataConnector.kafka

import java.util
import java.util.concurrent.atomic.AtomicBoolean

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import nl.solarboatteam.DataConnector.kafka.serialization.{JsonDeserializer, JsonSerializer}
import nl.solarboatteam.DataConnector.DataConnector
import nl.solarboatteam.DataConnector.models.data.{Data, SignalUpdate}
import org.apache.kafka.clients.consumer.{ConsumerRecord, KafkaConsumer}
import org.apache.kafka.clients.consumer.internals.NoOpConsumerRebalanceListener
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.{Deserializer, Serializer, StringDeserializer, StringSerializer}

import scala.collection.JavaConverters._

class KafkaConnector[V](private val connectConfig: util.Map[String, Object], private val client: String, serializer: Serializer[V], deserializer: Deserializer[V]) extends Runnable {
  private val consumer = new KafkaConsumer[String, V](connectConfig, new StringDeserializer, deserializer)
  private val producer = new KafkaProducer[String, V](connectConfig, new StringSerializer, serializer)
  private val subject = PublishSubject.create[ConsumerRecord[String, V]]()
  private val fromTopic = TopicHelper.getFromJsonTopic(client)
  private val toTopic = TopicHelper.getToJsonTopic(client)
  private var thread: Thread = _
  private var stopThread = new AtomicBoolean(false)

  def start(): Unit = {
    if (thread != null && thread.isAlive) {
      throw new RuntimeException("KafkaConnector was already started.")
    }

    consumer.subscribe(
      List(fromTopic).asJava,
      new NoOpConsumerRebalanceListener)

    thread = new Thread(this)
    thread.start()
  }


  def getObservable: Observable[ConsumerRecord[String, V]] = {
    subject
  }

  def send(key: String, value: V): Unit = {
    val newRecord = new ProducerRecord[String, V](toTopic, key, value)
    producer.send(newRecord)
  }

  def stop(): Unit = {
    stopThread.set(true)
  }

  def run(): Unit = {
    while (true) {
      val records = consumer.poll(10000).asScala

      if (stopThread.get()) {
        consumer.close()
        stopThread.set(false)
        return
      }

      records.foreach(record => {
        //val signal = new K(record.key(), record.value())
        subject.onNext(record)
      })
    }
  }
}
