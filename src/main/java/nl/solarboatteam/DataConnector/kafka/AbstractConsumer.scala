package nl.solarboatteam.DataConnector.kafka

import java.util.concurrent.atomic.AtomicBoolean
import java.util.regex.Pattern

import org.apache.kafka.clients.consumer.internals.NoOpConsumerRebalanceListener
import org.apache.kafka.clients.consumer.{ConsumerRecord, KafkaConsumer}
import org.slf4j.{Logger, LoggerFactory}
import rx.Observable
import rx.subjects.PublishSubject

import scala.collection.JavaConverters._

class AbstractConsumer[V](private val consumer : KafkaConsumer[String,V])
  extends Runnable {

  val LOG: Logger = LoggerFactory.getLogger(classOf[AbstractConsumer[V]])

  private val subject = PublishSubject.create[ConsumerRecord[String, V]]()
  private var thread: Thread = _
  private var stopThread = new AtomicBoolean(false)

  def subscribe(topic : String) : Unit = {
    consumer.subscribe(
      List(topic).asJava)
    LOG.info("subscribed to {}", topic)
  }

  def subscribe(topics : List[String]) : Unit = {
    consumer.subscribe(topics.asJava)
    LOG.info("subscribed to {}", topics)
  }

  def subscribe(topicPattern : Pattern) : Unit = {
    consumer.subscribe(topicPattern, new NoOpConsumerRebalanceListener)
    LOG.info("subscribed to {}", topicPattern)
  }

  def start(): Unit = {
    if (thread != null && thread.isAlive) {
      throw new RuntimeException("KafkaConnector was already started.")
    }


    thread = new Thread(this)
    thread.start()
  }
  
  def getObservable: Observable[ConsumerRecord[String, V]] = {
    subject.filter(x => x.value() != null)
  }

  def stop(): Unit = {
    stopThread.set(true)
  }

  def run(): Unit = {
    LOG.info("Started polling for data.")
    while (true) {
      val records = consumer.poll(10000).asScala

      if (stopThread.get()) {
        consumer.close()
        stopThread.set(false)
        LOG.info("Stopped polling for data.")
        return
      }

      records.foreach(record => {
        LOG.debug("Received data with key {} and value {}", record.key(), record.value())
        subject.onNext(record)
      })
    }
  }
}
