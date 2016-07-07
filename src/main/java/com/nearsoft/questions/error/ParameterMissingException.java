package com.nearsoft.questions.error;

public class ParameterMissingException extends RuntimeException {

    private static final String MESSAGE = "Parameter %s missing";

    public ParameterMissingException(String paramName) {
        super(String.format(MESSAGE, paramName));
    }
}
