package com.nearsoft.questions.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nearsoft.questions.controller.form.QuestionForm;
import com.nearsoft.questions.domain.auth.User;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Entity
@Indexed
public class Question implements Serializable, Authored {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_seq")
    @SequenceGenerator(name = "question_seq", sequenceName = "question_seq")
    private Long id;

    @Column(nullable = false)
    @Field
    private String title;

    @Column(nullable = false)
    @Field
    private String description;

    @IndexedEmbedded
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();

    @Column(nullable = false)
    private Integer totalAnswers = 0;

    @IndexedEmbedded
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    @ManyToOne(optional = false)
    private User user;

    public Question() {
    }

    public Question(QuestionForm form, List<Tag> persistedTags, User user) {
        this.title = form.getTitle();
        this.description = form.getDescription();
        this.user = user;
        List<String> requestedTagNames = form.getNormalizedTagList();

        if (CollectionUtils.isNotEmpty(persistedTags)) {
            this.tags.addAll(persistedTags);
            requestedTagNames.removeIf(new Predicate<String>() {
                @Override
                public boolean test(String tagName) {
                    if (tagName != null) {
                        String requestedTagName = StringUtils.trimWhitespace(tagName);
                        return persistedTags.contains(new Tag(requestedTagName.toLowerCase()));
                    }
                    return false;
                }
            });
        }
        for (String requestedTagName : requestedTagNames) {
            this.tags.add(new Tag(requestedTagName));
        }
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

    public Integer getTotalAnswers() {
        return totalAnswers;
    }

    public void setTotalAnswers(Integer totalAnswers) {
        this.totalAnswers = totalAnswers;
    }

    @JsonIgnore
    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public User getAuthor() {
        return getUser();
    }

    @Override
    public void setAuthor(User user) {
        setUser(user);
    }
}
