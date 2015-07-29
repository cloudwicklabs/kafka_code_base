# Kafka Producer, Consumer examples using Java and Scala API's

This repository contains code examples of Kafka consumer's, producer's using Scala and Java API.

## Building the reposiory

    sbt assembly
    
## Running Examples

### Producer

1. Create a kafka topic

        bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 \ 
        --partitions 4 --topic newtopic
        
2. Run the producer example to generate some random messages into the topic

        java -cp target/scala-2.11/ScalaKafkaExamples-assembly-1.0.jar \ 
        com.cloudwick.kafka.java.producer.examples.SimpleProducer \
        localhost:9093,localhost:9094 newtopic 100
        
3. Once written to the topic, you can check the consumer offset and log using
        
        bin/kafka-run-class.sh kafka.tools.ConsumerOffsetChecker --zookeeper localhost:2181 \ 
        --topic newtopic --group newgroup
        
### Consumer using high-level API
        
1. Running a single-threaded consumer

        java -cp target/scala-2.11/ScalaKafkaExamples-assembly-1.0.jar \ 
        com.cloudwick.kafka.java.consumer.examples.SimpleHighLevelConsumer \ 
        localhost:2181 newgroup newtopic

2. Running a multi-threaded consumer
 
        

        