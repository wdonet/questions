package com.nearsoft.questions.repository;

import com.nearsoft.questions.domain.RuleAnswerTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleAnswerTransactionRepository extends CrudRepository<RuleAnswerTransaction, Long> {

}
