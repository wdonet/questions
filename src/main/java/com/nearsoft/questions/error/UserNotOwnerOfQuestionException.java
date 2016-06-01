package com.nearsoft.questions.error;

public class UserNotOwnerOfQuestionException extends Throwable {

    public static final String MESSAGE = "This user %d is not the owner of the question, owner id: %d";

    public UserNotOwnerOfQuestionException(Long userId, Long ownerId) {
        super(String.format(MESSAGE, userId, ownerId));
    }
}
