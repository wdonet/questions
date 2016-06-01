package com.nearsoft.questions.repository;

import com.nearsoft.questions.domain.RuleQuestionTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleQuestionTransactionRepository extends CrudRepository<RuleQuestionTransaction, Long> {

}
