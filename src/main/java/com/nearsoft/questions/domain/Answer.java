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
    @SequenceGenerator(name = "answer_seq", sequenceName = "answer_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false)
    @JsonIgnore
    private Question question;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
    private List<AnswerComment> comments = new ArrayList<>();

    @Column(nullable = false)
    private Integer votesUp = 0;

    @Column(nullable = false)
    private Integer votesDown = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 35, nullable = false)
    private ItemStatus status;

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

    public Integer getVotesUp() {
        return votesUp;
    }

    public void setVotesUp(Integer votesUp) {
        this.votesUp = votesUp;
    }

    public Integer getVotesDown() {
        return votesDown;
    }

    public void setVotesDown(Integer votesDown) {
        this.votesDown = votesDown;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public List<AnswerComment> getComments() {
		return comments;
	}

	public void setComments(List<AnswerComment> comments) {
		this.comments = comments;
	}
}
