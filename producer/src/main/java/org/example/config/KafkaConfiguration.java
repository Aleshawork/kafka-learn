package org.example.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.services.*;
import org.example.KafkaProperties;
import org.example.UserAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfiguration {


    private Map<String, Object> senderProps(KafkaProperties properties) {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, properties.getProducerName());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UserActionSerializer.class.getName());
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomByNumberPartitioner.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, UserAction> producerFactory(KafkaProperties properties, ProducerFactory.Listener producerListener) {
        DefaultKafkaProducerFactory<String, UserAction> producerFactory = new DefaultKafkaProducerFactory<>(senderProps(properties));
        producerFactory.addListener(producerListener);
        return producerFactory;
    }

    @Bean
    public KafkaTemplate<String, UserAction> kafkaTemplate(ProducerFactory<String, UserAction> producerFactory) {
        return new KafkaTemplate(producerFactory);
    }

    @Bean
    public SendUserActionService sendUserActionService(KafkaTemplate<String, UserAction> kafkaTemplate, KafkaProperties kafkaProperties) {
        return new SpringKafkaSendUserActionService(kafkaTemplate, kafkaProperties);
    }


}
