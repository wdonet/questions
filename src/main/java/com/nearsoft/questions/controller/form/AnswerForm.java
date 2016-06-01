package com.nearsoft.questions.controller.form;

public class AnswerForm {
    private String _description;
    private Long _answerId;
    private Long _questionId;

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public Long getAnswerId() {
        return _answerId;
    }

    public void setAnswerId(Long answerId) {
        _answerId = answerId;
    }

    public Long getQuestionId() {
        return _questionId;
    }

    public void setQuestionId(Long questionId) {
        _questionId = questionId;
    }

    @Override
    public String toString() {
        return "AnswerForm {" +
            ", _description='" + _description + '\'' +
            ", _questionId=" + _questionId +
            '}';
    }
}
