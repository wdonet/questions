package com.nearsoft.questions.error;

public class TagNotFoundException extends NSQuestionsException {

    private static final String MESSAGE = "Not able to find tag with id: ";

    public TagNotFoundException(Long tagId) {
        super(MESSAGE + tagId);
    }
}
