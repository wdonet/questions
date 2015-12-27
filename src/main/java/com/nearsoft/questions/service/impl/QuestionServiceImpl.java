package com.nearsoft.questions.service.impl;

import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.repository.QuestionRepository;
import com.nearsoft.questions.search.service.HibernateSearchService;
import com.nearsoft.questions.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final HibernateSearchService _hibernateSearchService;
    private final QuestionRepository _questionRepository;

    @Autowired
    public QuestionServiceImpl(HibernateSearchService hibernateSearchService, QuestionRepository questionRepository) {
        _hibernateSearchService = hibernateSearchService;
        _questionRepository = questionRepository;
    }

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
        return _hibernateSearchService.search(Question.class, query, new String[]{"_title", "_description", "_tags._name",
                "_answers._description"});
    }
}
