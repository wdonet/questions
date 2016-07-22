package com.nearsoft.questions.error;

public class QuestionNotFoundException extends ElementNotFoundException {

    public static final String QUESTION = "Question";

    public QuestionNotFoundException(Long questionId) {
        super(QUESTION, questionId);
    }
}
