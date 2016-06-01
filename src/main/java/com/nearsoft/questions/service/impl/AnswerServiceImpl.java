package com.nearsoft.questions.service.impl;

import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.RuleAnswerTransaction;
import com.nearsoft.questions.domain.RuleName;
import com.nearsoft.questions.repository.AnswerRepository;
import com.nearsoft.questions.repository.RuleAnswerTransactionRepository;
import com.nearsoft.questions.repository.RuleRepository;
import com.nearsoft.questions.service.AnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private RuleAnswerTransactionRepository ruleAnswerTransactionRepository;
    @Autowired
    private RuleRepository _ruleRepository;

    @Override
    public void save(Answer answer) {
        answerRepository.save(answer);
        saveWithRuleName(answer, RuleName.NEW_ANSWER);
    }

    @Override
    public Answer get(final Long id){
    	return answerRepository.findOne(id);
    }

    @Override
    public void downvote(Answer answer) {
        answer.setVotesDown(answer.getVotesDown() + 1);
        answerRepository.save(answer);
        saveWithRuleName(answer, RuleName.VOTED_DOWN_ANSWER);
    }

    @Override
    public void upvote(Answer answer) {
        answer.setVotesUp(answer.getVotesUp() + 1);
        answerRepository.save(answer);
        saveWithRuleName(answer, RuleName.VOTED_UP_ANSWER);
    }

    private void saveWithRuleName(Answer answer, RuleName ruleName) {
        int points = _ruleRepository.findFirstByRuleName(ruleName).getPoints();
        RuleAnswerTransaction transaction = new RuleAnswerTransaction();
        transaction.setPoints(points);
        transaction.setRuleName(ruleName);
        transaction.setAnswerId(answer.getId());
        transaction.setCreatedAt(ZonedDateTime.now());

        ruleAnswerTransactionRepository.save(transaction);
    }
}
