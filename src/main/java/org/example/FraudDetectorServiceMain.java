package org.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class FraudDetectorServiceMain {

    public static void main( String[] args ) {
        var consumer = new KafkaConsumer<String, String>(properties());

        consumer.subscribe(Collections.singleton("ECOMMERCE_NEW_ORDER"));
        while(true) {
            var records = consumer.poll(Duration.ofMillis(100));

            if (!records.isEmpty()) {
                System.out.println("-----//-----//-----//-----//-----//-----//-----");
                System.out.println("Encontrei " + records.count() + " registros");
                for (var record : records) {
                    System.out.println("Processing new order, checking for fraud: " + "key=" + record.key() + "value=" + record.value());
                }
            }
        }
    }

    private static Properties properties() {
        var properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorServiceMain.class.getSimpleName());

        return properties;
    }
}
