package com.nearsoft.questions.service.impl;

import java.util.Arrays;
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
        //todo
    }

    @Override
    public Question get(long id) {
        //todo wdonet :
//        Question question = new Question().withTitle("What time<br/> is it\n now?");
//        question.setDescription("Don't know my time nor my TZ, help!");
//        question.setTags(Arrays.asList(new Tag("Finance")));
        return _questionRepository.findOne(id);
    }

    @Override
    public List<Question> search(String query) {
        //todo wdonet :
        return Arrays.asList(new Question().withTitle("What time<br/> is it\n now?"));
    }
}
