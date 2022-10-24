package org.example;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewOrderMain
{
    public static void main( String[] args ) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String, String>(properties());
        var value = "121212, 131, 1200";
        var email = "Thank you for your order! We are processing your order!";
        var record = new ProducerRecord<String, String>("ECOMMERCE_NEW_ORDER", value, value);
        var emailRecord = new ProducerRecord<String, String>("ECOMMERCE_SEND_EMAIL", email, email);

        Callback callback = (data, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
            }
            System.out.println("Sucesso, Topic: " + data.topic() + ", Partition: " + data.partition() + ", OffSet: " + data.offset() + ", TimeStamp: " + data.timestamp());
        };

        producer.send(record, callback).get();
        producer.send(emailRecord, callback).get();
    }

    private static Properties properties() {
        var properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;
    }
}
