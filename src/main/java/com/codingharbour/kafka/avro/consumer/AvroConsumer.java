package com.codingharbour.kafka.avro.consumer;

import com.codingharbour.avro.AvroSimpleMessage;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializerConfig;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class AvroConsumer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "avro-consumer-group");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);

        properties.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

        AvroConsumer consumer = new AvroConsumer();
        consumer.readMessages(properties);

    }

    public void readMessages(Properties props) {
        Consumer<String, AvroSimpleMessage> kafkaConsumer = new KafkaConsumer<String, AvroSimpleMessage>(props);
        kafkaConsumer.subscribe(Collections.singleton("avro-topic"));
        try {
            while (true) {
                ConsumerRecords<String, AvroSimpleMessage> records = kafkaConsumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, AvroSimpleMessage> record : records) {
                    System.out.println("Avro message content: " + record.value().getContent());
                    System.out.println("Avro message timestamp: " + record.value().getDateTime());
                }
                kafkaConsumer.commitAsync();
            }
        } finally {
            kafkaConsumer.close();
        }
    }
}
