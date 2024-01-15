package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserActionProcessor {
    private static final Logger log = LoggerFactory.getLogger(UserActionProcessor.class);

    public void processUserAction(UserAction action) {
        log.info("Process user action: {}", action);
    }
}
