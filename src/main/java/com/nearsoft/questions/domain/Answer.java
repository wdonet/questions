package com.nearsoft.questions.domain;

public class Answer {
    private long _id;
    private Question _question;
    private String _description;

    public long getId() {
        return _id;
    }

    public void setId(long id) {
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
