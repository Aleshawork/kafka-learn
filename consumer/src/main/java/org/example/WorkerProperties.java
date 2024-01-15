package org.example;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(WorkerProperties.PREFIX)
public class WorkerProperties {
    public static final String PREFIX = "kafka.worker";
    private int treadCount = 64;

    public int getTreadCount() {
        return treadCount;
    }

    public void setTreadCount(int treadCount) {
        this.treadCount = treadCount;
    }
}
