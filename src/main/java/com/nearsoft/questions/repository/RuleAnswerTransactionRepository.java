package com.nearsoft.questions.repository;

import java.util.List;
import com.nearsoft.questions.domain.RuleAnswerTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleAnswerTransactionRepository extends CrudRepository<RuleAnswerTransaction, Long> {

    /**
     * This should only be used when ADMIN is sure of all the consequences. Look at AnswerServiceImpl # safeDeleteAnswersOfQuestion().
     * @param ids answer ids to be deleted
     */
    void deleteByAnswerIdIn(List<Long> ids);
}
