package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class App 
{
    public static void main( String[] args ) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String, String>(properties());
        var value = "121212, 131, 1200";
        var record = new ProducerRecord<String, String>("ECOMMERCE_NEW_ORDER", value, value);

        producer.send(record, (data, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
            }

            System.out.println("Sucesso, Topic: "+ data.topic() + ", Partition: " + data.partition() + ", OffSet: " + data.offset() + ", TimeStamp: " + data.timestamp());
        }).get();
    }

    private static Properties properties() {
        var properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;
    }
}