package com.nearsoft.questions.domain;

import com.nearsoft.questions.domain.auth.User;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Indexed
public class Answer implements Serializable, Authored {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_seq")
    @SequenceGenerator(name = "answer_seq", sequenceName = "answer_seq")
    private Long id;

    @ManyToOne(optional = false)
    private Question question;

    @Column(nullable = false)
    @Field
    private String description;

    @ManyToOne(optional = false)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
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

    @Override
    public User getAuthor() {
        return getUser();
    }

    @Override
    public void setAuthor(User user) {
        setUser(user);
    }
}
