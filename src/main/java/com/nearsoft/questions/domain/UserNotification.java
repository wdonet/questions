package com.nearsoft.questions.domain;

import com.nearsoft.questions.domain.auth.User;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "user_notification")
public class UserNotification implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_notification_seq")
    @SequenceGenerator(name = "user_notification_seq", sequenceName = "user_notification_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(targetEntity = Notification.class, optional = false)
    private Notification notification;

    @ManyToOne(targetEntity = User.class, optional = false)
    private User user;

    @Column(name = "email_delivered")
    private Boolean emailDelivered;

    @Column(name = "ui_notified")
    private Boolean uiNotified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getEmailDelivered() {
        return emailDelivered;
    }

    public void setEmailDelivered(Boolean emailDelivered) {
        this.emailDelivered = emailDelivered;
    }

    public Boolean getUiNotified() {
        return uiNotified;
    }

    public void setUiNotified(Boolean uiNotified) {
        this.uiNotified = uiNotified;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
