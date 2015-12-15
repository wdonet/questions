package com.nearsoft.questions.service;

import java.util.List;
import com.nearsoft.questions.domain.Question;

public interface QuestionService {

    void save(Question question);

    void updateTotalAnswers(Question question);

    Question get(long id);

    List<Question> search(String query);

}
