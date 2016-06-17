package com.nearsoft.questions.service;

import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.error.UserNotOwnerOfQuestionException;

public interface AnswerService {

    void save(Answer answer);

    void downvote(Answer answer);

    void upvote(Answer answer);

    void markAsAccepted(Long answerId, User user) throws UserNotOwnerOfQuestionException;

    Answer get(final Long id);
}
