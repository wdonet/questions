package com.nearsoft.questions.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class AnswerComment extends AbstractAuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_comments_seq")
    @SequenceGenerator(name = "answer_comments_seq", sequenceName = "answer_comments_seq")
	private Long id;
    
    @Column(nullable = false)
	private String description;
	
	@ManyToOne
	@JoinColumn(name="answer_id", nullable=false)
	@JsonIgnore
	private Answer answer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	
	
	
}
