package com.nearsoft.questions.error;

public class OperationDeniedException extends RuntimeException {

    public static final String MESSAGE = "You are not allowed to perform this operation";

    public OperationDeniedException() {
        super(MESSAGE);
    }
}
