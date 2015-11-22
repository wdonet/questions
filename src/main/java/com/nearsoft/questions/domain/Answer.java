package com.nearsoft.questions.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class Answer implements Serializable {
    @Id
    @GeneratedValue
    private Long _id;
    @ManyToOne(optional = false)
    private Question _question;
    @Column(nullable = false)
    private String _description;

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
}
