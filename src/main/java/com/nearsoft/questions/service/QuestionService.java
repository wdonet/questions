package com.nearsoft.questions.service;

import java.util.List;
import com.nearsoft.questions.domain.Question;
import org.springframework.data.domain.Page;

public interface QuestionService {

    int PAGE_SIZE = 20;

    void save(Question question);

    Question get(long id);

    List<Question> search(String query);

    Page<Question> getUnanswered(int uiPageNumber, int pageSize);

    Page<Question> getNewest(int uiPageNumber, int pageSize);

    Page<Question> getNewestByTag(long tagId, int uiPageNumber, int pageSize);

    boolean isOnlyOneAnswer();

    public void update(Question question);

}
