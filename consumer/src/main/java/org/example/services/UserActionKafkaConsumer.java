package org.example.services;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.example.KafkaProperties;
import org.example.UserAction;
import org.example.config.WorkerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserActionKafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(UserActionKafkaConsumer.class);

    private final Consumer<String, UserAction> consumer;
    private final KafkaProperties properties;
    private final ExecutorService kafkaWorkerExecutor;
    private final UserActionProcessor userActionProcessor;
    private static final AtomicLong threadId = new AtomicLong(0);
    private volatile boolean exitFlag = true;

    private final Thread  consumerThread = new Thread(
            this::runConsumer,
            this.getClass().getSimpleName()
    );




    public UserActionKafkaConsumer(
            Consumer<String, UserAction> consumer,
            KafkaProperties properties,
            WorkerProperties workerProperties,
            UserActionProcessor userActionProcessor) {
        this.consumer = consumer;
        this.properties = properties;
        kafkaWorkerExecutor = Executors.newFixedThreadPool(
                workerProperties.getTreadCount(),
                runnable -> new Thread(runnable, "KafkaWorker-" + threadId.incrementAndGet())
        );
        this.userActionProcessor = userActionProcessor;
    }

    @EventListener
    public void startEventCycle(ContextRefreshedEvent event) {
        consumerThread.start();
    }

    void runConsumer() {
            consumer.subscribe(List.of(properties.getTopic()));
            while (exitFlag) {
                log.info("Получение сообщения от брокера");
                final ConsumerRecords<String,UserAction> consumerRecords = consumer.poll(Duration.ofMillis(5000));
                boolean messageProcessingNotFinished = false;
                do {
                    try {
                        log.info("Start of processing received messages! Received: {}", consumerRecords.count());
                        processMessages(consumerRecords);
                        messageProcessingNotFinished = false;
                    } catch (Exception ex) {
                        messageProcessingNotFinished = true;
                        log.error("Error in processing !");
                    }

                } while (messageProcessingNotFinished);
                consumer.commitAsync();
            }
    }

    private void processMessages(ConsumerRecords<String, UserAction> consumerRecords) throws InterruptedException {
        Iterable<ConsumerRecord<String, UserAction>> iterable = () -> consumerRecords.iterator();

        log.info(
                "Обработка набора сообщений из partition: {}",
                StreamSupport.stream(iterable.spliterator(), false).map(ConsumerRecord::partition).collect(Collectors.toSet())
        );

        Map<String, List<UserAction>> taskMap = StreamSupport.stream(iterable.spliterator(), false).map(ConsumerRecord::value)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(UserAction::getUserId));

        CountDownLatch countDownLatch = new CountDownLatch(taskMap.size());
        for (List<UserAction> userActions: taskMap.values()) {
            kafkaWorkerExecutor.submit(() -> {
                for (UserAction userAction: userActions) {
                        userActionProcessor.processUserAction(userAction);
                        countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
    }


}
