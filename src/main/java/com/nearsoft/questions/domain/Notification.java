package com.nearsoft.questions.domain;

import com.nearsoft.questions.domain.auth.User;

import javax.persistence.*;

@Entity(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_seq")
    @SequenceGenerator(name = "notification_seq", sequenceName = "notification_seq", allocationSize = 1)
    private Long id;

    private String description;

    @ManyToOne(targetEntity = User.class, optional = false)
    private User user;

    @ManyToOne(targetEntity = Question.class, optional = false)
    private Question question;

    @Column(name = "email_delivered")
    private boolean emailDelivered;

    @Column(name = "ui_notified")
    private Boolean uiNotified;

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

    public Boolean isUiNotified() {
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
