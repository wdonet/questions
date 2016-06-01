package com.nearsoft.questions.service;

import com.nearsoft.questions.domain.Answer;

public interface AnswerService {

    void save(Answer answer);

    void downvote(Answer answer);

    void upvote(Answer answer);
}
