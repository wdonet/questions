package com.nearsoft.questions.error;

public class ElementNotFoundException extends RuntimeException {

    public static final String MESSAGE = "Not able to find %s with id %d";

    public ElementNotFoundException(String element, Long questionId) {
        super(String.format(MESSAGE, element, questionId));
    }
}
