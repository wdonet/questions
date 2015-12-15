package com.nearsoft.questions.service.impl;

import java.util.List;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.repository.AnswerRepository;
import com.nearsoft.questions.repository.QuestionRepository;
import com.nearsoft.questions.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final Logger _log = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuestionRepository _questionRepository;

    @Autowired
    private AnswerRepository _answerRepository;

    @Override
    public void save(Question question) {
        _questionRepository.save(question);
    }

    @Override
    public synchronized void updateTotalAnswers(Question question) {
        int total = _answerRepository.countBy_question__id(question.getId());
        if (_log.isDebugEnabled()) {
            _log.debug(String.format("Updating question %d with total answers: %d", question.getId(), total));
        }
        question.setTotalAnswers(total);
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
