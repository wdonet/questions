package com.nearsoft.questions.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Answer extends AbstractAuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_seq")
    @SequenceGenerator(name = "answer_seq", sequenceName = "answer_seq")
    private Long id;

    @ManyToOne(optional = false)
    private Answer answer;

    @Column(nullable = false)
    private String description;

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

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
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
}
