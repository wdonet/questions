package com.nearsoft.questions.service;

import com.nearsoft.questions.domain.Answer;

public interface AnswerService {

    void save(Answer answer);
    Answer get(final Long id);
}
