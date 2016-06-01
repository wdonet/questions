package com.nearsoft.questions.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Answer extends AbstractAuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_seq")
    @SequenceGenerator(name = "answer_seq", sequenceName = "answer_seq")
    private Long id;

    @ManyToOne(optional = false)
    @JsonIgnore
    private Question question;

    @Column(nullable = false)
    private String description;
    
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuestionComment> comments = new ArrayList<>();

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
    
    public List<QuestionComment> getComments() {
		return comments;
	}

	public void setComments(List<QuestionComment> comments) {
		this.comments = comments;
	}
}
