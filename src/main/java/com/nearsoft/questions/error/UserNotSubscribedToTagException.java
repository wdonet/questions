package com.nearsoft.questions.error;

import com.nearsoft.questions.domain.auth.User;

public class UserNotSubscribedToTagException extends NSQuestionsException {

    private static final String MESSAGE = "User %s not subscribed to tag with id %d";

    public UserNotSubscribedToTagException(User userName, Long tagId) {
        super(String.format(MESSAGE, userName.getEmail(), tagId));
    }
}
