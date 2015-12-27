package com.nearsoft.questions.domain;

import com.nearsoft.questions.controller.form.QuestionForm;
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
public class Question implements Serializable {
    @Id
    @GeneratedValue
    private Long _id;
    @Column(nullable = false)
    @Field
    private String _title;
    @Column(nullable = false)
    @Field
    private String _description;
    @IndexedEmbedded
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Tag> _tags = new ArrayList<>();
    @Column(nullable = false)
    private Integer _totalAnswers = 0;
    @IndexedEmbedded
    @OneToMany(mappedBy = "_question", cascade = CascadeType.ALL)
    private List<Answer> _answers = new ArrayList<>();

    public Question() {}

    public Question(QuestionForm dto, List<Tag> persistedTags) {
        this._title = dto.getTitle();
        this._description = dto.getTitle();
        List<String> requestedTagNames = dto.getNormalizedTagList();

        if (CollectionUtils.isNotEmpty(persistedTags)) {
            this._tags.addAll(persistedTags);
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
            this._tags.add(new Tag(requestedTagName));
        }
    }

    public Question withTitle(String title) {
        setTitle(title);
        return this;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
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
        if (_tags == null) {
            _tags = new ArrayList<>();
        }
        _tags.add(tag);
    }

    public Integer getTotalAnswers() {
        return _totalAnswers;
    }

    public void setTotalAnswers(Integer totalAnswers) {
        _totalAnswers = totalAnswers;
    }

    public List<Answer> getAnswers() {
        return _answers;
    }

    public void setAnswers(List<Answer> answers) {
        _answers = answers;
    }
}
