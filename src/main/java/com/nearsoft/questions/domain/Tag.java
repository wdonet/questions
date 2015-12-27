package com.nearsoft.questions.domain;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Indexed
public class Tag implements Serializable {
    @Id
    @GeneratedValue
    private Long _id;
    @Column(nullable = false)
    @Field
    private String _name;

    public Tag() { }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Tag tag = (Tag) o;
        return _name.equals(tag._name);
    }

    @Override
    public int hashCode() {
        return _name.hashCode();
    }
}
