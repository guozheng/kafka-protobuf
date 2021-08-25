package com.codingharbour.kafka.avro.producer;

import com.codingharbour.avro.AvroSimpleMessage;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Instant;
import java.util.Properties;

public class AvroProducer {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        properties.put(KafkaProtobufSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

        Producer<String, AvroSimpleMessage> kafkaProducer =
                new KafkaProducer<String, AvroSimpleMessage>(properties);
        AvroProducer producer = new AvroProducer();
        producer.writeMessage(kafkaProducer, "Hello Avro");

    }

    public void writeMessage(Producer<String, AvroSimpleMessage> producer, String content) {
        AvroSimpleMessage message = AvroSimpleMessage.newBuilder()
                .setContent(content)
                .setDateTime(String.valueOf(Instant.now().toEpochMilli()))
                .build();
        ProducerRecord<String, AvroSimpleMessage> record =
                new ProducerRecord<String, AvroSimpleMessage>("avro-topic", null, message);
        producer.send(record);
        producer.flush();
        producer.close();
    }
}
