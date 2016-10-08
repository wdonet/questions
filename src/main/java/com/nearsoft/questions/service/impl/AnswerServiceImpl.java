package com.nearsoft.questions.service.impl;

import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.ItemStatus;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.RuleAnswerTransaction;
import com.nearsoft.questions.domain.RuleName;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.error.AnswerNotFoundException;
import com.nearsoft.questions.error.OperationDeniedException;
import com.nearsoft.questions.repository.AnswerCommentRepository;
import com.nearsoft.questions.repository.AnswerRepository;
import com.nearsoft.questions.service.AnswerService;
import com.nearsoft.questions.service.NotificationDelivererService;
import com.nearsoft.questions.service.NotificationService;
import com.nearsoft.questions.service.RuleService;
import com.nearsoft.questions.service.impl.deliverer.AcceptedAnswerNotifierServiceImpl;
import com.nearsoft.questions.service.impl.deliverer.AnswerVotedNotifierServiceImpl;
import com.nearsoft.questions.service.impl.deliverer.NewAnswerNotifierServiceImpl;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private AnswerRepository answerRepository;

    private AnswerCommentRepository answerCommentRepository;

    private RuleService ruleService;

    private NotificationService notificationService;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository, AnswerCommentRepository answerCommentRepository, RuleService ruleService, NotificationService notificationService) {
        this.answerRepository = answerRepository;
        this.answerCommentRepository = answerCommentRepository;
        this.ruleService = ruleService;
        this.notificationService = notificationService;
    }

    @Override
    public Answer save(Answer answer) {
        Answer savedAnswer = answerRepository.save(answer);

        NotificationDelivererService delivererService = notificationService.getDelivererInstance(NewAnswerNotifierServiceImpl.class);
        Map<String, String> notificationSettings = new HashMap<>();

        notificationSettings.put(NewAnswerNotifierServiceImpl.ANSWER_ID_PARAM, "" + answer.getId());

        delivererService.sendNotification(notificationSettings);

        ruleService.savePointsForAnswer(answer, RuleName.NEW_ANSWER);

        return savedAnswer;
    }

    @Override
    public void update(Answer answer) {
        answerRepository.save(answer);
    }

    @Override
    public Answer get(final Long id) {
        return answerRepository.findOne(id);
    }

    @Override
    public void downVote(Long answerId, User currentUser) {
        Answer answer = get(answerId);

        if (answer == null) {
            log.warn("Answer " + answerId + " not found when voting down");
            return;
        }

        User answerOwner = answer.getUser();

        if (answerOwner.getId().equals(currentUser.getId())) {
            log.warn("Trying to vote down owned answer: " + answerId + " [" + answerOwner.getEmail() + "] vs [" + currentUser.getEmail() + "]");
            return;
        }

        if (ruleService.isValidUserPermission(RuleName.VOTED_DOWN_ANSWER, currentUser)) {
            answer.setVotesDown(answer.getVotesDown() + 1);
            answerRepository.save(answer);

            savePointsForAnswer(answer, RuleName.VOTED_DOWN_ANSWER);
        }
    }

    @Override
    public void upVote(Long answerId, User currentUser) {
        Answer answer = get(answerId);

        if (answer == null) {
            log.warn("Answer " + answerId + " not found when voting up");
            return;
        }

        User answerOwner = answer.getUser();

        if (answerOwner.getId().equals(currentUser.getId())) {
            log.warn("Trying to vote up owned answer: " + answerId + " [" + answerOwner.getEmail() + "] vs [" + currentUser.getEmail() + "]");
            return;
        }

        if (ruleService.isValidUserPermission(RuleName.VOTED_UP_ANSWER, currentUser)) {
            answer.setVotesUp(answer.getVotesUp() + 1);
            answerRepository.save(answer);

            savePointsForAnswer(answer, RuleName.VOTED_UP_ANSWER);
        }
    }

    public List<Answer> findByUser(User user) {
        return answerRepository.findByUser(user);
    }

    /**
     * This should only be used when ADMIN is sure of all the consequences. Elements to be deleted are:
     *  1. Rule Answer Transactions (reputation related)
     *  2. Comments on each Answer of the question
     *  3. All answers for the specified question
     *  Please keep this updated in the order the elements are deleted
     * @param questionId identifier of the question
     */
    @Transactional
    @Override
    public void safeDeleteAnswersOfQuestion(Long questionId) {
        List<Long> ids = answerRepository.getAnswerIdsByQuestionId(questionId);
        ruleService.deleteTransactionsByAnswerIds(ids);
        answerCommentRepository.deleteByAnswerIdIn(ids);
        answerRepository.deleteByIdIn(ids);
    }

    /**
     * This should only be used when ADMIN is sure of all the consequences. Elements to be deleted are:
     *  1. Rule Answer Transactions (reputation related)
     *  2. Comments for Answer
     *  3. Answer
     *  Please keep this updated in the order the elements are deleted
     * @param answerId identifier of the answer
     */
    @Transactional
    @Override
    public void safeDeleteAnswer(Long answerId) {
        List<Long> ids = Collections.singletonList(answerId);
        ruleService.deleteTransactionsByAnswerIds(ids);
        answerCommentRepository.deleteByAnswerIdIn(ids);
        answerRepository.delete(answerId);
    }

    private void savePointsForAnswer(Answer answer, RuleName ruleName) {
        RuleAnswerTransaction ruleAnswerTransaction = ruleService.savePointsForAnswer(answer, ruleName);

        NotificationDelivererService delivererService = notificationService.getDelivererInstance(AnswerVotedNotifierServiceImpl.class);
        Map<String, String> notificationSettings = new HashMap<>();

        notificationSettings.put(AnswerVotedNotifierServiceImpl.ANSWER_ID_PARAM, "" + answer.getId());
        notificationSettings.put(AnswerVotedNotifierServiceImpl.POINTS_PARAM, "" + ruleAnswerTransaction.getPoints());

        delivererService.sendNotification(notificationSettings);
    }

    @Override
    public void markAsAccepted(Long answerId, User user) {
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

            NotificationDelivererService delivererService = notificationService.getDelivererInstance(AcceptedAnswerNotifierServiceImpl.class);
            Map<String, String> notificationSettings = new HashMap<>();

            notificationSettings.put(AcceptedAnswerNotifierServiceImpl.ANSWER_ID_PARAM, "" + answerId);

            delivererService.sendNotification(notificationSettings);

            ruleService.savePointsForAnswer(answer, RuleName.ACCEPTED_ANSWER);
        }
    }


    private boolean questionHasAnyAcceptedAnswer(Question question) {
        return CollectionUtils.isNotEmpty(question.getAnswers()) &&
                question.getAnswers().stream().filter(answer -> answer.getStatus().equals(ItemStatus.ACCEPTED)).count() > 0;
    }
}
