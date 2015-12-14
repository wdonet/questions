package com.nearsoft.questions.error;

public class QuestionNotFoundException extends Throwable {

    public static final String MESSAGE = "Not able to find question with id %d";

    public QuestionNotFoundException(Long questionId) {
        super(String.format(MESSAGE, questionId));
    }
}
