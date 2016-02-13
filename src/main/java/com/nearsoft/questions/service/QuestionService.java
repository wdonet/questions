package com.nearsoft.questions.service;

import java.util.List;
import com.nearsoft.questions.domain.Question;
import org.springframework.data.domain.Page;

public interface QuestionService {

    int PAGE_SIZE = 20;

    void save(Question question);

    void updateTotalAnswers(Question question);

    Question get(long id);

    List search(String query);

    Page<Question> getUnanswered(int UIPageNumber, int pageSize);

    Page<Question> getNewest(int UIPageNumber, int pageSize);

    Page<Question> getNewestByTag(long tagId, int UIPageNumber, int pageSize);
}
