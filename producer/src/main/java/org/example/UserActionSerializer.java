package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserActionSerializer implements Serializer<UserAction> {

    private static final Logger log = LoggerFactory.getLogger(UserActionSerializer.class);


    private final ObjectMapper objectMapper;

    public UserActionSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] serialize(String topic, UserAction data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException ex) {
            log.warn("Ошибка при сериализации сообщения!");
            return new byte[0];
        }
    }
}
