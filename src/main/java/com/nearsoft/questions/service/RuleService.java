package com.nearsoft.questions.service;

import java.util.List;
import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.RuleAnswerTransaction;
import com.nearsoft.questions.domain.RuleName;
import com.nearsoft.questions.domain.RuleQuestionTransaction;
import com.nearsoft.questions.domain.auth.User;

public interface RuleService {

    List<RuleName> getUserPermissions(Integer userReputation);

    boolean isValidUserPermission(RuleName ruleName, User currentUser);

    RuleQuestionTransaction savePointsForQuestion(Question question, RuleName ruleName);

    RuleAnswerTransaction savePointsForAnswer(Answer answer, RuleName ruleName);

}
