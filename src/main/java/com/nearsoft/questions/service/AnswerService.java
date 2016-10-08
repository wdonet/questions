package com.nearsoft.questions.service;

import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.auth.User;

import java.util.List;

public interface AnswerService {

    Answer save(Answer answer);

    void update(Answer answer);

    void downVote(Long answerId, User user);

    void upVote(Long answerId, User user);

    void markAsAccepted(Long answerId, User user);

    Answer get(final Long id);

    List<Answer> findByUser(User user);

    void safeDeleteAnswersOfQuestion(Long questionId);

    void safeDeleteAnswer(Long answerId);
}
