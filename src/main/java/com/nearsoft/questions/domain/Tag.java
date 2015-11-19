package com.nearsoft.questions.domain;

public class Tag {
    private String _name;

    public Tag(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }
}
