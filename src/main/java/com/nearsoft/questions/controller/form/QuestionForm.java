package com.nearsoft.questions.controller.form;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.commons.lang3.StringUtils;

public class QuestionForm {
    private String _title;
    private String _description;
    private String _tags;
    private List<String> _normalizedTagList;

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

    /**
     * This is only for the Form Binding in Spring MVC.
     * Use #getNormalizedTagList instead.
     * @return comma delimited tag list as a String
     */
    public String getTags() {
        return _tags;
    }

    public void setTags(String tags) {
        _tags = tags;
    }

    public List<String> getNormalizedTagList() {
        if (_normalizedTagList == null) {
            StringTokenizer tokenizer = new StringTokenizer(_tags, ",");
            _normalizedTagList = new ArrayList<>();
            while (tokenizer.hasMoreTokens()) {
                String normalized = StringUtils.trimToNull(tokenizer.nextToken());
                if (normalized != null) {
                    _normalizedTagList.add(normalized.toLowerCase());
                }
            }
        }
        return _normalizedTagList;
    }

    @Override
    public String toString() {
        return "QuestionForm {" +
            ", _title='" + _title + '\'' +
            ", _description='" + _description + '\'' +
            ", _tags='" + _tags + '\'' +
            '}';
    }
}
