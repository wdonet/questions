package com.nearsoft.questions.controller.form;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.commons.lang3.StringUtils;

public class QuestionForm {

    private Long id;
    private String title;
    private String description;
    private String tags;
    private List<String> normalizedTagList = new ArrayList<>();

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

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    /**
     * This is only for the Form Binding in Spring MVC.
     * Use #getNormalizedTagList instead which automatically adds the "notag" Tag by default.
     * @return comma delimited tag list as a String
     */
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<String> getNormalizedTagList(boolean withNoTagElement) {
        StringTokenizer tokenizer = new StringTokenizer(tags, ",");
        normalizedTagList.clear();
        while (tokenizer.hasMoreTokens()) {
            String normalized = StringUtils.trimToNull(tokenizer.nextToken());
            if (normalized != null) {
                normalizedTagList.add(normalized.toLowerCase());
            }
        }
        if (withNoTagElement) {
            normalizedTagList.add("notag");
        }
        return normalizedTagList;
    }

    @Override
    public String toString() {
        return "QuestionForm {" +
            "_id='" + id + '\'' +
            ", _title='" + title + '\'' +
            ", _description='" + description + '\'' +
            ", _tags='" + tags + '\'' +
            '}';
    }
}
