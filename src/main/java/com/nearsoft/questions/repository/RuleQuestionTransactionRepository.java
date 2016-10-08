package com.nearsoft.questions.repository;

import com.nearsoft.questions.domain.RuleQuestionTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleQuestionTransactionRepository extends CrudRepository<RuleQuestionTransaction, Long> {

    /**
     * This should only be used when ADMIN is sure of all the consequences. Look at AnswerServiceImpl # safeDeleteAnswersOfQuestion().
     * @param questionId question id used to delete transactions
     */
    void deleteByQuestionIdIn(Long questionId);
}
