package com.nearsoft.questions.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Tag implements Serializable {
    @Id
    @GeneratedValue
    private Long _id;
    @Column(nullable = false)
    private String _name;

    public Tag(String name) {
        _name = name;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }
}
