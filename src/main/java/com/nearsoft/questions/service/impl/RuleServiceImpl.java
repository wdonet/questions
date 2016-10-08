package com.nearsoft.questions.service.impl;

import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.Rule;
import com.nearsoft.questions.domain.RuleAnswerTransaction;
import com.nearsoft.questions.domain.RuleName;
import com.nearsoft.questions.domain.RuleQuestionTransaction;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.repository.RuleAnswerTransactionRepository;
import com.nearsoft.questions.repository.RuleQuestionTransactionRepository;
import com.nearsoft.questions.repository.RuleRepository;
import com.nearsoft.questions.service.RuleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    private RuleRepository ruleRepository;
    private RuleQuestionTransactionRepository ruleQuestionTransactionRepository;
    private RuleAnswerTransactionRepository ruleAnswerTransactionRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public RuleServiceImpl(RuleRepository ruleRepository, RuleQuestionTransactionRepository ruleQuestionTransactionRepository,
                           RuleAnswerTransactionRepository ruleAnswerTransactionRepository) {
        this.ruleRepository = ruleRepository;
        this.ruleQuestionTransactionRepository = ruleQuestionTransactionRepository;
        this.ruleAnswerTransactionRepository = ruleAnswerTransactionRepository;
    }

    @Override
    public List<RuleName> getUserPermissions(Integer userReputation) {
        List<RuleName> permissions = new ArrayList<>();
        if (userReputation != null && userReputation > 0) {
            for (Rule rule : ruleRepository.findAll()) {
                if (userReputation >= rule.getReputationNeeded()) {
                    permissions.add(rule.getRuleName());
                }
            }
        }
        return permissions;
    }

    @Override
    public boolean isValidUserPermission(RuleName ruleName, User currentUser) {
        log.info("this is the null user " + currentUser);
        int reputationNeeded = ruleRepository.findFirstByRuleName(ruleName).getReputationNeeded();
        int userReputation = currentUser.getReputation();
        log.info(String.format("Validating %s if points needed [%d] and user have [%d]", ruleName, reputationNeeded, userReputation));
        return userReputation >= reputationNeeded;
    }

    @Override
    public RuleQuestionTransaction savePointsForQuestion(Question question, RuleName ruleName) {
        int points = ruleRepository.findFirstByRuleName(ruleName).getPoints();
        RuleQuestionTransaction ruleQuestionTransaction = new RuleQuestionTransaction();
        ruleQuestionTransaction.setCreatedAt(ZonedDateTime.now());
        ruleQuestionTransaction.setPoints(points);
        ruleQuestionTransaction.setQuestionId(question.getId());
        ruleQuestionTransaction.setRuleName(ruleName);
        ruleQuestionTransactionRepository.save(ruleQuestionTransaction);

        return ruleQuestionTransaction;
    }

    @Override
    public RuleAnswerTransaction savePointsForAnswer(Answer answer, RuleName ruleName) {
        int points = ruleRepository.findFirstByRuleName(ruleName).getPoints();
        RuleAnswerTransaction transaction = new RuleAnswerTransaction();
        transaction.setPoints(points);
        transaction.setRuleName(ruleName);
        transaction.setAnswerId(answer.getId());
        transaction.setCreatedAt(ZonedDateTime.now());
        ruleAnswerTransactionRepository.save(transaction);

        return transaction;
    }

    /**
     * This should only be used when ADMIN is sure of all the consequences. Look at AnswerServiceImpl # safeDeleteAnswersOfQuestion().
     * @param ids answer ids used to delete transactions
     */
    @Override
    public void deleteTransactionsByAnswerIds(List<Long> ids) {
        ruleAnswerTransactionRepository.deleteByAnswerIdIn(ids);
    }

    /**
     * This should only be used when ADMIN is sure of all the consequences. Look at AnswerServiceImpl # safeDeleteAnswersOfQuestion().
     * @param questionId question id used to delete transactions
     */
    @Override
    public void deleteTransactionsByQuestionId(Long questionId) {
        ruleQuestionTransactionRepository.deleteByQuestionIdIn(questionId);
    }

}
