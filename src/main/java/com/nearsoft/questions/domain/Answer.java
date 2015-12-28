package com.nearsoft.questions.domain;

import com.nearsoft.questions.domain.auth.User;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Indexed
public class Answer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_seq")
    @SequenceGenerator(name = "answer_seq", sequenceName = "answer_seq")
    private Long _id;
    @ManyToOne(optional = false)
    private Question _question;
    @Column(nullable = false)
    @Field
    private String _description;
    @ManyToOne(optional = false)
    private User _user;

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        _id = id;
    }

    public Question getQuestion() {
        return _question;
    }

    public void setQuestion(Question question) {
        _question = question;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        this._description = description;
    }

    public User getUser() {
        return _user;
    }

    public void setUser(User user) {
        this._user = user;
    }
}
