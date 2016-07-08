package com.nearsoft.questions.service;

import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.auth.User;

public interface AnswerService {

    void save(Answer answer);

    public void update(Answer answer);

    void downvote(Answer answer);

    void upvote(Answer answer);

    void markAsAccepted(Long answerId, User user);

    Answer get(final Long id);
}
