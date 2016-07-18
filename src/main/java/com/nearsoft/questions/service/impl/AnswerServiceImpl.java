package com.nearsoft.questions.service.impl;

import com.nearsoft.questions.domain.*;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.error.AnswerNotFoundException;
import com.nearsoft.questions.error.OperationDeniedException;
import com.nearsoft.questions.repository.AnswerRepository;
import com.nearsoft.questions.service.AnswerService;
import com.nearsoft.questions.service.NotificationDelivererService;
import com.nearsoft.questions.service.NotificationService;
import com.nearsoft.questions.service.RuleService;
import com.nearsoft.questions.service.impl.deliverer.NewQuestionNotifierServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private AnswerRepository answerRepository;

    private RuleService ruleService;

    NotificationService notificationService;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository, RuleService ruleService, NotificationService notificationService) {
        this.answerRepository = answerRepository;
        this.ruleService = ruleService;
        this.notificationService = notificationService;
    }

    @Override
    public void save(Answer answer) {
        answerRepository.save(answer);


        Question question = answer.getQuestion();

        NotificationDelivererService delivererService = notificationService.getDelivererInstance(NewQuestionNotifierServiceImpl.class);
        Map<String, String> notificationSettings = new HashMap<>();

        notificationSettings.put(NewQuestionNotifierServiceImpl.QUESTION_ID_PARAM, "" + question.getId());
        notificationSettings.put(NewQuestionNotifierServiceImpl.DESCRIPTION_PARAM, "New answer:" + answer.getDescription());

        delivererService.sendNotification(notificationSettings);

        ruleService.savePointsForAnswer(answer, RuleName.NEW_ANSWER);
    }

    @Override
    public void update(Answer answer) {
        answerRepository.save(answer);
    }

    @Override
    public Answer get(final Long id){
    	return answerRepository.findOne(id);
    }

    @Override
    public void downVote(Long answerId, User currentUser) {
        Answer answer = get(answerId);
        if (answer != null && ruleService.isValidUserPermission(RuleName.VOTED_DOWN_ANSWER, currentUser)) {
            answer.setVotesDown(answer.getVotesDown() + 1);
            answerRepository.save(answer);
            ruleService.savePointsForAnswer(answer, RuleName.VOTED_DOWN_ANSWER);
        }
        else {
            log.warn("Answer " + answerId + " not found when voting down");
        }
    }

    @Override
    public void upVote(Long answerId, User currentUser) {
        Answer answer = get(answerId);
        if (answer != null && ruleService.isValidUserPermission(RuleName.VOTED_UP_ANSWER, currentUser)) {
            answer.setVotesUp(answer.getVotesUp() + 1);
            answerRepository.save(answer);
            ruleService.savePointsForAnswer(answer, RuleName.VOTED_UP_ANSWER);
        }
        else {
            log.warn("Answer " + answerId + " not found when voting up");
        }
    }

    @Override
    public void markAsAccepted(Long answerId, User user){
        Answer answer = answerRepository.findOne(answerId);
        if (answer == null) {
            log.error("Answer not found");
            throw new AnswerNotFoundException(answerId);
        }
        if (!answer.getStatus().equals(ItemStatus.ACCEPTED) && !questionHasAnyAcceptedAnswer(answer.getQuestion())) {
//        boolean acceptingOwnAnswer = answer.getUser().getId().equals(user.getId());  //todo should we accept own answers?
            boolean isQuestionOwner = answer.getQuestion().getUser().getId().equals(user.getId());
            if (!isQuestionOwner) {
                throw new OperationDeniedException();
            }

            for (Answer otherAnswer : answer.getQuestion().getAnswers()) {
                if (otherAnswer.getId().equals(answerId)) {
                    continue;
                }
                otherAnswer.setStatus(ItemStatus.NOT_ACCEPTED);
                answerRepository.save(otherAnswer);
            }

            answer.setStatus(ItemStatus.ACCEPTED);
            answerRepository.save(answer);
            ruleService.savePointsForAnswer(answer, RuleName.ACCEPTED_ANSWER);
        }
    }

    private boolean questionHasAnyAcceptedAnswer(Question question) {
        return CollectionUtils.isNotEmpty(question.getAnswers()) &&
            question.getAnswers().stream().filter(answer -> answer.getStatus().equals(ItemStatus.ACCEPTED)).count() > 0;
    }
}
