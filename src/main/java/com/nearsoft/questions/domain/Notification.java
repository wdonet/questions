package com.nearsoft.questions.domain;

import com.nearsoft.questions.domain.auth.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "notification")
public class Notification {

    @Id
    private Long id;
    private String description;
    private LocalDateTime date;

    @ManyToOne(targetEntity = User.class, optional = false)
    private User user;

    @Column(name = "email_delivered")
    private boolean emailDelivered;

    @Column(name = "ui_notified")
    private boolean uiNotified;

    @Column(name = "notification_type")
    @Enumerated
    private NotificationType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isEmailDelivered() {
        return emailDelivered;
    }

    public void setEmailDelivered(boolean emailDelivered) {
        this.emailDelivered = emailDelivered;
    }

    public boolean isUiNotified() {
        return uiNotified;
    }

    public void setUiNotified(boolean uiNotified) {
        this.uiNotified = uiNotified;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }
}
