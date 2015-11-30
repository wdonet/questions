package com.nearsoft.questions.service.impl;

import java.util.List;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.repository.QuestionRepository;
import com.nearsoft.questions.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository _questionRepository;

    @Override
    public void save(Question question) {
        _questionRepository.save(question);
    }

    @Override
    public Question get(long id) {
        return _questionRepository.findOne(id);
    }

    @Override
    public List<Question> search(String query) {
        return _questionRepository.findByTitleILike(query);
    }
}
