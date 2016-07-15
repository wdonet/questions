package com.nearsoft.questions.error;

public class AnswerNotFoundException extends ElementNotFoundException {

    public static final String ANSWER = "Answer";

    public AnswerNotFoundException(Long answerId) {
        super(ANSWER, answerId);
    }
}
