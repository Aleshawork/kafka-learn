package org.example.services;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.example.UserAction;

import java.util.concurrent.Future;

public interface SendUserActionService {
    Future<RecordMetadata> sendUserAction(UserAction userAction);
}
