package com.nearsoft.questions.repository;

import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.auth.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long> {

    @RestResource(exported = false)
    int countByQuestionId(long questionId);

    @Override
    @RestResource(exported = false)
    void delete(Long id);

    @Override
    @RestResource(exported = false)
    void delete(Answer entity);

    @RestResource(exported = false)
    List<Answer> findByUser(User user);

}
