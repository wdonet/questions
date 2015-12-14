package com.nearsoft.questions.service.impl;

import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.repository.AnswerRepository;
import com.nearsoft.questions.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository _answerRepository;

    @Override
    public void save(Answer answer) {
        _answerRepository.save(answer);
    }

}
