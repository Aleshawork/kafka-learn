package org.example;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(KafkaProperties.PREFIX)
public class KafkaProperties {

    public static final String PREFIX = "kafka.connection";

    private List<String> bootstrapServers = new ArrayList<>();
    private String producerName;
    private String consumerGroupId;
    private String topic;
    private String acsk;
    private String enabledIdidempotence;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<String> getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(List<String> bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getConsumerGroupId() {
        return consumerGroupId;
    }

    public void setConsumerGroupId(String consumerGroupId) {
        this.consumerGroupId = consumerGroupId;
    }

    public String getAcsk() {
        return acsk;
    }

    public void setAcsk(String acsk) {
        this.acsk = acsk;
    }

    public String getEnabledIdidempotence() {
        return enabledIdidempotence;
    }

    public void setEnabledIdidempotence(String enabledIdidempotence) {
        this.enabledIdidempotence = enabledIdidempotence;
    }
}
