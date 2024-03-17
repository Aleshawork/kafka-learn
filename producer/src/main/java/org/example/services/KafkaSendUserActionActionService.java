package org.example.services;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.example.KafkaProperties;
import org.example.UserAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;


public class KafkaSendUserActionActionService implements SendUserActionService {
    private static final Logger log = LoggerFactory.getLogger(KafkaSendUserActionActionService.class);

    private final KafkaProperties kafkaProperties;
    private final Producer<String, UserAction> producer;

    public KafkaSendUserActionActionService(KafkaProperties kafkaProperties, Producer<String, UserAction> producer) {
        this.kafkaProperties = kafkaProperties;
        this.producer = producer;
    }

    @Override
    public CompletableFuture<RecordMetadata> sendUserAction(UserAction userAction) {
        log.info("Before sends messages!");
        ProducerRecord<String, UserAction> record =
                new ProducerRecord<>(
                        kafkaProperties.getTopic(),
                        userAction.getUserId(),
                        userAction
                );
        CallbackCompletableFuture callbackCompletableFuture = new CallbackCompletableFuture();
        producer.send(record, callbackCompletableFuture);
        log.info("Sends messages!");
        return callbackCompletableFuture;
    }

    private static class CallbackCompletableFuture extends CompletableFuture<RecordMetadata> implements Callback {

        @Override
        public void onCompletion(RecordMetadata metadata, Exception exception) {
            if (exception == null) {
                log.info("CallbackCompletableFuture - action.");
                complete(metadata);
            } else {
                completeExceptionally(exception);
            }
        }
    }

}
