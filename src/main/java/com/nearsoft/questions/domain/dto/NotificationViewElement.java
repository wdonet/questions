package com.nearsoft.questions.domain.dto;


import com.nearsoft.questions.domain.Notification;
import com.nearsoft.questions.domain.NotificationType;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.UserNotification;

import org.json.simple.JSONObject;

import java.sql.Timestamp;


public class NotificationViewElement {

    private Long id;
    private String description;
    private Boolean uiNotified;
    private NotificationType type;
    private Timestamp createdAt;


    public NotificationViewElement(Notification notification, UserNotification userNotification) {
        this.id = userNotification.getId();
        this.description = notification.getDescription();
        this.uiNotified = userNotification.getUiNotified();
        this.type = notification.getType();
        this.createdAt = notification.getCreatedAt();
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getUiNotified() {
        return uiNotified;
    }

    public void setUiNotified(Boolean uiNotified) {
        this.uiNotified = uiNotified;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
