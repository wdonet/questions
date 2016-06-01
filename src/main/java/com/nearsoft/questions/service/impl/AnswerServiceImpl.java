package com.nearsoft.questions.service.impl;

import java.time.ZonedDateTime;
import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.RuleAnswerTransaction;
import com.nearsoft.questions.domain.RuleName;
import com.nearsoft.questions.repository.AnswerRepository;
import com.nearsoft.questions.repository.RuleAnswerTransactionRepository;
import com.nearsoft.questions.repository.RuleRepository;
import com.nearsoft.questions.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private RuleAnswerTransactionRepository ruleAnswerTransactionRepository;
    @Autowired
    private RuleRepository _ruleRepository;

    @Override
    public void save(Answer answer) {
        answerRepository.save(answer);

        int points = _ruleRepository.findFirstByRuleName(RuleName.NEW_ANSWER).getPoints();
        RuleAnswerTransaction transaction = new RuleAnswerTransaction();
        transaction.setPoints(points);
        transaction.setRuleName(RuleName.NEW_ANSWER);
        transaction.setQuestionId(answer.getQuestion().getId());
        transaction.setCreatedAt(ZonedDateTime.now());

        ruleAnswerTransactionRepository.save(transaction);
    }
}
