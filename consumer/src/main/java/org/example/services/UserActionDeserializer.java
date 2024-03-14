package org.example.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.example.UserAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class UserActionDeserializer implements Deserializer<UserAction> {
    private static final Logger log = LoggerFactory.getLogger(UserActionDeserializer.class);
    private final ObjectMapper objectMapper;

    public UserActionDeserializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public UserAction deserialize(String topic, byte[] data) {
        if (data.length == 0) {
            return null;
        }
        try {
            return objectMapper.readValue(data, UserAction.class);
        } catch (IOException ex) {
            String message = new String(data, StandardCharsets.UTF_8);
            log.error("Unable to deserialize message {}", message, ex);
            return null;
        }
    }
}
