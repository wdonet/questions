package com.nearsoft.questions.repository;

import com.nearsoft.questions.domain.Rule;
import com.nearsoft.questions.domain.RuleName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends CrudRepository<Rule, Long> {

    Rule findFirstByRuleName(RuleName ruleName);
}
