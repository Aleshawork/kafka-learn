package org.example.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.CustomByNumberPartitioner;
import org.example.KafkaProperties;
import org.example.UserAction;
import org.example.UserActionSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaConfiguration {

    @Bean(destroyMethod = "close")
    public KafkaProducer<String, UserAction> createProducer(KafkaProperties properties, UserActionSerializer userActionSerializer) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, properties.getProducerName());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomByNumberPartitioner.class);
        return new KafkaProducer<>(props, null, userActionSerializer);
    }

}
