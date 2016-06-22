package com.nearsoft.questions.service.impl;

import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nearsoft.questions.domain.Answer;

import com.nearsoft.questions.domain.Question;

import com.nearsoft.questions.domain.RuleAnswerTransaction;
import com.nearsoft.questions.domain.RuleName;
import com.nearsoft.questions.domain.ItemStatus;
import com.nearsoft.questions.domain.RuleAnswerTransaction;
import com.nearsoft.questions.domain.RuleName;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.error.UserNotOwnerOfQuestionException;

import com.nearsoft.questions.repository.AnswerRepository;
import com.nearsoft.questions.repository.RuleAnswerTransactionRepository;
import com.nearsoft.questions.repository.RuleRepository;
import com.nearsoft.questions.service.AnswerService;

import com.nearsoft.questions.service.NotificationDelivererService;
import com.nearsoft.questions.service.NotificationService;
import com.nearsoft.questions.service.impl.deliverer.NewQuestionNotifierServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private RuleAnswerTransactionRepository ruleAnswerTransactionRepository;
    @Autowired
    private RuleRepository _ruleRepository;

    @Autowired
    NotificationService notificationService;

    @Override
    public void save(Answer answer) {
        answerRepository.save(answer);


        Question question = answer.getQuestion();

        NotificationDelivererService delivererService = notificationService.getDelivererInstance(NewQuestionNotifierServiceImpl.class);
        Map<String, String> notificationSettings = new HashMap<>();

        notificationSettings.put(NewQuestionNotifierServiceImpl.QUESTION_ID_PARAM, "" + question.getId());
        notificationSettings.put(NewQuestionNotifierServiceImpl.DESCRIPTION_PARAM, "New answer:" + answer.getDescription());

        delivererService.sendNotification(notificationSettings);

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

    @Override
    public void markAsAccepted(Long answerId, User user) throws UserNotOwnerOfQuestionException{

        Answer answer = answerRepository.findOne(answerId);
        if (!answer.getQuestion().getUser().getId().equals(user.getId())) {
            throw new UserNotOwnerOfQuestionException(user.getId(), answer.getQuestion().getUser().getId());
        }

        for (Answer otherAnswer :answer.getQuestion().getAnswers()) {
            otherAnswer.setStatus(ItemStatus.NOT_ACCEPTED);
            answerRepository.save(otherAnswer);
        }

        answer.setStatus(ItemStatus.ACCEPTED);
        answerRepository.save(answer);
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
