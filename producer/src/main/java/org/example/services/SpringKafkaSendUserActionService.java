package org.example.services;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.example.KafkaProperties;
import org.example.UserAction;
import org.example.listener.ProducerListenerCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;


public class SpringKafkaSendUserActionService implements SendUserActionService {

    private final KafkaTemplate<String, UserAction> kafkaTemplate;
    private final KafkaProperties kafkaProperties;
    @Autowired
    private ProducerListenerCallback producerListenerCallback;

    public SpringKafkaSendUserActionService(KafkaTemplate<String, UserAction> kafkaTemplate, KafkaProperties kafkaProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaProperties = kafkaProperties;
    }

    @Override
    public ListenableFuture<RecordMetadata> sendUserAction(UserAction userAction) {
        ProducerRecord<String, UserAction> sendRecord =
                new ProducerRecord<>(
                        kafkaProperties.getTopic(),
                        userAction.getUserId(),
                        userAction
                );
        ListenableFuture sendFuture = kafkaTemplate.send(sendRecord);

        sendFuture.addCallback(producerListenerCallback);

        return sendFuture;
    }
}
