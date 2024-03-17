package org.example.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * Используется при асинхронной отправке сообщений для информирования о результате обработки
 * сообщения на сервере
 */
@Component
public class ProducerListenerCallback implements ListenableFutureCallback<SendResult> {

    Logger logger = LoggerFactory.getLogger(ProducerListenerCallback.class);


    @Override
    public void onFailure(Throwable ex) {
        logger.error("Ошибка при обработке сообщения на сервере:{}", ex.getMessage());
    }

    @Override
    public void onSuccess(SendResult result) {
        logger.info("Сообщение успешно обработано offset:{}, timestamp:{}", result.getRecordMetadata().offset(), result.getRecordMetadata().timestamp());
    }
}
