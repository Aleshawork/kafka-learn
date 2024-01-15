package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


public class UserAction {

    @JsonProperty("message_id")
    private final String messageId;
    @JsonProperty("user_id")
    private final String userId;
    @JsonProperty("action_type")
    private final long actionType;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UserAction(
            @JsonProperty("message_id") String messageId,
            @JsonProperty("user_id") String userId,
            @JsonProperty("action_type") long actionType
    ) {
        this.messageId = Objects.requireNonNull(messageId);
        this.userId =  Objects.requireNonNull(userId);
        this.actionType = actionType;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getUserId() {
        return userId;
    }

    public long getActionType() {
        return actionType;
    }
}
