package org.example.controller;

import org.example.UserAction;
import org.example.services.KafkaSendUserActionActionService;
import org.example.services.SendUserActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class UserActionController {
    private static final Logger log = LoggerFactory.getLogger(UserActionController.class);
    private final SendUserActionService sendService;

    public UserActionController(SendUserActionService sendService) {
        this.sendService = sendService;
    }

    @PostMapping(value = "/userActionList", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void userAction(@RequestBody List<UserAction> actions) throws ExecutionException, InterruptedException {
        log.info("Получены данные в контролере в количестве: {}", actions.size());
        Future[] completableFutures = actions.stream()
                .map(userAction -> sendService.sendUserAction(userAction))
                .toArray(Future[]::new);

    }

}
