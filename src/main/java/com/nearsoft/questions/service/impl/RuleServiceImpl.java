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
import com.nearsoft.questions.service.NotificationDelivererService;
import com.nearsoft.questions.service.NotificationService;
import com.nearsoft.questions.service.RuleService;
import com.nearsoft.questions.service.impl.deliverer.AnswerVotedNotifierServiceImpl;
import com.nearsoft.questions.service.impl.deliverer.QuestionVotedNotifierServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RuleServiceImpl implements RuleService {

    private RuleRepository ruleRepository;
    private RuleQuestionTransactionRepository ruleQuestionTransactionRepository;
    private RuleAnswerTransactionRepository ruleAnswerTransactionRepository;
    private NotificationService notificationService;

    @Value("${com.nsquestions.notification.question-voted-up.subject:Question voted up}")
    private String subjectQuestionVotedUp;

    @Value("${com.nsquestions.notification.question-voted-down.subject:Question voted down}")
    private String subjectQuestionVotedDown;

    @Value("${com.nsquestions.notification.answer-voted-up.subject:Answer voted up}")
    private String subjectAnswerVotedUp;

    @Value("${com.nsquestions.notification.answer-voted-down.subject:Answer voted down}")
    private String subjectAnswerVotedDown;

    @Value("${com.nsquestions.notification.affectedPointsMessage:Affected points}")
    private String affectedPointsMessage;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public RuleServiceImpl(RuleRepository ruleRepository, RuleQuestionTransactionRepository ruleQuestionTransactionRepository,
                           RuleAnswerTransactionRepository ruleAnswerTransactionRepository, NotificationService notificationService) {
        this.ruleRepository = ruleRepository;
        this.ruleQuestionTransactionRepository = ruleQuestionTransactionRepository;
        this.ruleAnswerTransactionRepository = ruleAnswerTransactionRepository;
        this.notificationService = notificationService;
    }

    @Override
    public List<RuleName> getUserPermissions(Integer userReputation) {
        List<RuleName> permissions = new ArrayList<>();
        if (userReputation != null && userReputation > 0) {
            for (Rule rule : ruleRepository.findAll()) {
                if (userReputation > rule.getReputationNeeded()) {
                    permissions.add(rule.getRuleName());
                }
            }
        }
        return permissions;
    }

    @Override
    public boolean isValidUserPermission(RuleName ruleName, User currentUser) {
        int reputationNeeded = ruleRepository.findFirstByRuleName(ruleName).getReputationNeeded();
        int userReputation = currentUser.getReputation();
        log.info(String.format("Validating %s if points needed [%d] and user have [%d]", ruleName, reputationNeeded, userReputation));
        return userReputation > reputationNeeded;
    }

    @Override
    public void savePointsForQuestion(Question question, RuleName ruleName) {
        int points = ruleRepository.findFirstByRuleName(ruleName).getPoints();
        RuleQuestionTransaction ruleQuestionTransaction = new RuleQuestionTransaction();
        ruleQuestionTransaction.setCreatedAt(ZonedDateTime.now());
        ruleQuestionTransaction.setPoints(points);
        ruleQuestionTransaction.setQuestionId(question.getId());
        ruleQuestionTransaction.setRuleName(ruleName);
        ruleQuestionTransactionRepository.save(ruleQuestionTransaction);

        NotificationDelivererService delivererService = notificationService.getDelivererInstance(QuestionVotedNotifierServiceImpl.class);
        Map<String, String> notificationSettings = new HashMap<>();

        notificationSettings.put(QuestionVotedNotifierServiceImpl.QUESTION_ID_PARAM, "" + question.getId());
        notificationSettings.put(QuestionVotedNotifierServiceImpl.DESCRIPTION_PARAM, affectedPointsMessage + ": " + points);
        notificationSettings.put(QuestionVotedNotifierServiceImpl.SUBJECT_PARAM, points > 0 ? subjectQuestionVotedUp : subjectQuestionVotedDown);
        notificationSettings.put(QuestionVotedNotifierServiceImpl.POINTS_PARAM, "" + points);

        delivererService.sendNotification(notificationSettings);

    }

    @Override
    public void savePointsForAnswer(Answer answer, RuleName ruleName) {
        int points = ruleRepository.findFirstByRuleName(ruleName).getPoints();
        RuleAnswerTransaction transaction = new RuleAnswerTransaction();
        transaction.setPoints(points);
        transaction.setRuleName(ruleName);
        transaction.setAnswerId(answer.getId());
        transaction.setCreatedAt(ZonedDateTime.now());
        ruleAnswerTransactionRepository.save(transaction);

        NotificationDelivererService delivererService = notificationService.getDelivererInstance(AnswerVotedNotifierServiceImpl.class);
        Map<String, String> notificationSettings = new HashMap<>();

        notificationSettings.put(AnswerVotedNotifierServiceImpl.ANSWER_ID_PARAM, "" + answer.getId());
        notificationSettings.put(AnswerVotedNotifierServiceImpl.DESCRIPTION_PARAM, affectedPointsMessage + ": " + points);
        notificationSettings.put(AnswerVotedNotifierServiceImpl.SUBJECT_PARAM, points > 0 ? subjectAnswerVotedUp : subjectAnswerVotedDown);
        notificationSettings.put(AnswerVotedNotifierServiceImpl.POINTS_PARAM, "" + points);

        delivererService.sendNotification(notificationSettings);
    }

}
