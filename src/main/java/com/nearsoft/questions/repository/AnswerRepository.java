package com.nearsoft.questions.repository;

import com.nearsoft.questions.domain.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long> {

    int countByQuestionId(long questionId);

}
