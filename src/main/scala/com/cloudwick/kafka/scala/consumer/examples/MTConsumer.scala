package com.cloudwick.kafka.scala.consumer.examples

import java.util.Properties
import java.util.concurrent.Executors

import kafka.consumer.{KafkaStream, Consumer, ConsumerConfig}

/**
 * Simple multi-threaded Kafka Consumer implementation using Consumer HighLevel API
 */
object MTConsumer extends App {
  val props = new Properties()
  props.put("zookeeper.connect", "localhost:2181")
  props.put("group.id", "newgroup")
  props.put("zookeeper.session.timeout.ms", "400")
  props.put("zookeeper.sync.time.ms", "200")
  props.put("auto.commit.interval.ms", "1000")
  val consumerConfig = new ConsumerConfig(props)

  val consumerConnector = Consumer.create(consumerConfig)

  // make sure we provide one-to-one mapping for topic to threads
  val topicMap = Map(
    "newtopic" -> 4
  )

  val topicMessageStreams = consumerConnector.createMessageStreams(topicMap)

  val executorPool = Executors.newFixedThreadPool(topicMap.values.sum)

  topicMessageStreams.values.foreach { streams =>
    streams.foreach { stream =>
      executorPool.submit(new MessageHandler(stream))
    }
  }

  private class MessageHandler(stream: KafkaStream[Array[Byte], Array[Byte]]) extends Runnable {
    override def run(): Unit = {
      try {
        val streamIterator = stream.iterator()
        while (streamIterator.hasNext()) {
          val msg = streamIterator.next()
          println("Message: " + new String(msg.message()))
        }
      } catch {
        case e: Throwable => println(e.getMessage)
      }
    }
  }
}
