package com.nearsoft.questions.domain;

import com.nearsoft.questions.domain.auth.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class TagSubscription implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_subscription_seq")
    @SequenceGenerator(name = "question_subscription_seq", sequenceName = "question_subscription_seq")
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Tag tag;

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

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
