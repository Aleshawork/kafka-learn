package org.example.listener;

import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

/**
 * Используется для уведомления о факте подключения нового Producer к брокеру
 */
@Component
public class ProducerStartUpListener implements ProducerFactory.Listener {

    Logger log = LoggerFactory.getLogger(ProducerStartUpListener.class);


    @Override
    public void producerAdded(String id, Producer producer) {
        log.info("Producer добавлен с  id:{}", id);
        ProducerFactory.Listener.super.producerAdded(id, producer);
    }

    @Override
    public void producerRemoved(String id, Producer producer) {
        ProducerFactory.Listener.super.producerRemoved(id, producer);
    }
}
