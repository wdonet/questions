package com.nearsoft.questions.service.impl;

import java.util.Arrays;
import java.util.List;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.Tag;
import com.nearsoft.questions.service.QuestionService;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Override
    public void save(Question question) {
        //todo
    }

    @Override
    public Question get(long id) {
        //todo wdonet :
        Question question = new Question().withTitle("What time<br/> is it\n now?");
        question.setDescription("Don't know my time nor my TZ, help!");
        question.setTags(Arrays.asList(new Tag("Finance")));
        return question;
    }

    @Override
    public List<Question> search(String query) {
        //todo wdonet :
        return Arrays.asList(new Question().withTitle("What time<br/> is it\n now?"));
    }
}
