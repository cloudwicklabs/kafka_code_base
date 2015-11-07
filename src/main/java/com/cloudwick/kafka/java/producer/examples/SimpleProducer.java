package com.cloudwick.kafka.java.producer.examples;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Date;
import java.util.Properties;

/**
 * SimpleProducer is an example of how to implement Producer in Kafka, this example does not handles
 * partitioning of messages. Instead it will generate a random message each time and forwards it to
 * Kafka Broker's.
 */
public class SimpleProducer {
    private static Producer<String, String> producer;

    public SimpleProducer(String brokers) {
        Properties props = new Properties();

        props.put("metadata.broker.list", brokers);
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");

        ProducerConfig config = new ProducerConfig(props);
        producer = new Producer<String, String>(config);
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: SimpleProducer [brokers] [topic] [number_messages]");
            System.exit(1);
        }

        String brokers = (String) args[0];
        String topic = (String) args[1];
        String count = (String) args[2];
        int messageCount = Integer.parseInt(count);
        System.out.println("Brokers list - " + brokers);
        System.out.println("Topic Name - " + topic);
        System.out.println("Message Count - " + messageCount);

        SimpleProducer simpleProducer = new SimpleProducer(brokers);
        simpleProducer.publishMessage(topic, messageCount);
    }

    private void publishMessage(String topic, int messageCount) {
        for (int mCount = 0; mCount < messageCount; mCount++) {
            String runtime = new Date().toString();

            String msg = "Message Publishing Time - " + runtime;
            System.out.println(msg);
            // Creates a KeyedMessage instance
            KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, msg);

            // Publish the message
            producer.send(data);
        }
        // Close producer connection with broker.
        producer.close();
    }
}
