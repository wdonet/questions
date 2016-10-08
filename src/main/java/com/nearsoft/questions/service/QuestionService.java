package com.nearsoft.questions.service;

import java.util.List;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.auth.User;
import org.springframework.data.domain.Page;

public interface QuestionService {

    void save(Question question);

    void downVote(Long questionId, User user);

    void upVote(Long questionId, User user);

    Question get(long id);

    List<Question> search(String query);

    Page<Question> getUnanswered(int uiPageNumber, int pageSize);

    Page<Question> getNewest(int uiPageNumber, int pageSize);

    Page<Question> getNewestByTag(long tagId, int uiPageNumber, int pageSize);

    Page<Question> getNewestByCreator(String email, int uiPageNumber, int pageSize);

    void update(Question question);

    void safeDeleteQuestion(Long questionId);
}
