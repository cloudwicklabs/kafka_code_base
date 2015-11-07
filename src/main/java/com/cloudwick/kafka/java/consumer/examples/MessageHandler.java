package com.cloudwick.kafka.java.consumer.examples;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

/**
 * MessageHandler will take an individual Kafka Stream and consumes the messages from that Stream
 * and prints to the console.
 */
public class MessageHandler implements Runnable {
    private KafkaStream<byte[], byte[]> stream;

    public MessageHandler(KafkaStream<byte[], byte[]> s) {
        stream = s;
    }

    @Override
    public void run() {
        try {
            ConsumerIterator<byte[], byte[]> consumerIte = stream.iterator();
            while (consumerIte.hasNext()) {
                System.out.println("Message: " + new String(consumerIte.next().message()));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
