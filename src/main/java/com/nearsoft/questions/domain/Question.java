package com.nearsoft.questions.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nearsoft.questions.controller.form.QuestionForm;
import com.nearsoft.questions.domain.auth.User;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.annotations.Formula;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.util.StringUtils;

@Entity
@Document(indexName = "nsquestions", type = "question")
public class Question extends AbstractAuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_seq")
    @SequenceGenerator(name = "question_seq", sequenceName = "question_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer votesUp = 0;

    @Column(nullable = false)
    private Integer votesDown = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 35, nullable = false)
    private ItemStatus status;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @OrderBy("status ASC, votes_up DESC")
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuestionComment> comments = new ArrayList<>();

    @Formula("(select count(a.*) from Answer a where a.question_id = id)")
    private Integer totalAnswers;

    public Question() {
    }

    public Question(QuestionForm form, List<Tag> persistedTags, User user) {
        this.title = form.getTitle();
        this.description = form.getDescription();
        this.user = user;
        this.id = form.getId();
        List<String> requestedTagNames = form.getNormalizedTagList();

        if (CollectionUtils.isNotEmpty(persistedTags)) {
            this.tags.addAll(persistedTags);
            requestedTagNames.removeIf(tagName -> {
                if (tagName != null) {
                    String requestedTagName = StringUtils.trimWhitespace(tagName);
                    return persistedTags.contains(new Tag(requestedTagName.toLowerCase()));
                }
                return false;
            });
        }
        this.tags.addAll(requestedTagNames.stream().map(Tag::new).collect(Collectors.toList()));
    }

    public Question withTitle(String title) {
        setTitle(title);
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tag);
    }

    @JsonIgnore
    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public Integer getTotalAnswers() {
        return totalAnswers;
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

    public void setTotalAnswers(Integer totalAnswers) {
        this.totalAnswers = totalAnswers;
    }

    public List<QuestionComment> getComments() {
		return comments;
	}

	public void setComments(List<QuestionComment> comments) {
		this.comments = comments;
	}

    public boolean hasAnyAcceptedAnswer() {
        return CollectionUtils.isNotEmpty(getAnswers()) &&
            getAnswers().stream().filter(answer -> answer.getStatus().equals(ItemStatus.ACCEPTED)).count() > 0;
    }
}
