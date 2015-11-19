package com.nearsoft.questions.domain;

import java.util.ArrayList;
import java.util.List;

public class
    Question {

    private long _id;
    private String _title;
    private String _description;
    private List<Tag> _tags = new ArrayList<>();
    private int _totalAnswers;
    private List<Answer> _answers = new ArrayList<>();

    public Question withTitle(String title) {
        setTitle(title);
        return this;
    }

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        _id = id;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        _title = title;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public List<Tag> getTags() {
        return _tags;
    }

    public void setTags(List<Tag> tags) {
        _tags = tags;
    }

    public void addTag(Tag tag) {
        _tags.add(tag);
    }

    public int getTotalAnswers() {
        return _totalAnswers;
    }

    public void setTotalAnswers(int totalAnswers) {
        _totalAnswers = totalAnswers;
    }

    public List<Answer> getAnswers() {
        return _answers;
    }

    public void setAnswers(List<Answer> answers) {
        _answers = answers;
    }
}
