package com.nearsoft.questions.service.impl;

import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.ItemStatus;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.RuleName;
import com.nearsoft.questions.domain.RuleQuestionTransaction;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.repository.QuestionRepository;
import com.nearsoft.questions.repository.search.QuestionSearchRepository;
import com.nearsoft.questions.service.NotificationDelivererService;
import com.nearsoft.questions.service.NotificationService;
import com.nearsoft.questions.service.QuestionService;
import com.nearsoft.questions.service.RuleService;
import com.nearsoft.questions.service.impl.deliverer.NewQuestionNotifierServiceImpl;
import com.nearsoft.questions.service.impl.deliverer.QuestionVotedNotifierServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final QuestionSearchRepository questionSearchRepository;

    private RuleService ruleService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${questions.onlyOneAnswer}")
    private Boolean onlyOneAnswer;

    private NotificationService notificationService;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionSearchRepository questionSearchRepository, RuleService ruleService,
                               NotificationService notificationService) {
        this.questionRepository = questionRepository;
        this.questionSearchRepository = questionSearchRepository;
        this.ruleService = ruleService;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void save(Question question) {

        questionRepository.save(question);
        questionSearchRepository.save(question);

        NotificationDelivererService delivererService = notificationService.getDelivererInstance(NewQuestionNotifierServiceImpl.class);
        Map<String, String> notificationSettings = new HashMap<>();

        notificationSettings.put(NewQuestionNotifierServiceImpl.QUESTION_ID_PARAM, "" + question.getId());

        delivererService.sendNotification(notificationSettings);

        ruleService.savePointsForQuestion(question, RuleName.NEW_QUESTION);
    }

    @Override
    public void downVote(Long questionId, User currentUser) {
        Question question = get(questionId);
        if (question != null && ruleService.isValidUserPermission(RuleName.VOTED_DOWN_QUESTION, currentUser)) {
            question.setVotesDown(question.getVotesDown() + 1);
            questionRepository.save(question);

            savePointsForQuestion(question, RuleName.VOTED_DOWN_QUESTION);
        } else {
            log.warn("Question " + questionId + " not found when voting down");
        }
    }


    @Override
    public void upVote(Long questionId, User currentUser) {
        Question question = get(questionId);
        if (question != null && ruleService.isValidUserPermission(RuleName.VOTED_UP_QUESTION, currentUser)) {
            question.setVotesUp(question.getVotesUp() + 1);
            questionRepository.save(question);

            savePointsForQuestion(question, RuleName.VOTED_UP_QUESTION);
        } else {
            log.warn("Question " + questionId + " not found when voting up");
        }
    }

    private void savePointsForQuestion(Question question, RuleName ruleName) {
        RuleQuestionTransaction ruleQuestionTransaction = ruleService.savePointsForQuestion(question, ruleName);
        Map<String, String> notificationSettings = new HashMap<>();

        notificationSettings.put(QuestionVotedNotifierServiceImpl.QUESTION_ID_PARAM, "" + question.getId());
        notificationSettings.put(QuestionVotedNotifierServiceImpl.POINTS_PARAM, "" + ruleQuestionTransaction.getPoints());

        NotificationDelivererService delivererService = notificationService.getDelivererInstance(QuestionVotedNotifierServiceImpl.class);

        delivererService.sendNotification(notificationSettings);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void update(Question question) {

        validateQuestionStatus(question);
        addUserToNewTags(question);

        questionRepository.save(question);
        questionSearchRepository.save(question);
        log.info("Question updated: {}" + question);

    }

    private void addUserToNewTags(Question question) {
        question.getTags().stream().filter(tag -> tag.getId() == null).forEach(tag -> {
            tag.setUser(question.getUser());
        });
    }

    @Override
    public Question get(long id) {
        return questionRepository.findOne(id);
    }

    @Override
    public boolean isOnlyOneAnswer() {
        return onlyOneAnswer;
    }

    private void adjustNumberOfAnswers(Question one) {
        if (onlyOneAnswer) {
            Answer firstAnswer = one.getAnswers().stream().findFirst().get();
            one.getAnswers().clear();
            one.getAnswers().add(firstAnswer);
        }
    }

    @Override
    public Page<Question> getUnanswered(int uiPageNumber, int pageSize) {
        int validPageSize = getValidPageSize(pageSize);
        long totalRows = questionRepository.countByAnswersIsNull();
        int validPageNumber = getValidPageNumber(uiPageNumber, validPageSize, totalRows);
        Pageable pageable = new PageRequest(validPageNumber, validPageSize, Sort.Direction.DESC, "id");
        return questionRepository.findByAnswersIsNull(pageable);
    }

    @Override
    public Page<Question> getNewest(int uiPageNumber, int pageSize) {
        int validPageSize = getValidPageSize(pageSize);
        long totalRows = questionRepository.count();
        int validPageNumber = getValidPageNumber(uiPageNumber, validPageSize, totalRows);
        Pageable pageable = new PageRequest(validPageNumber, validPageSize, Sort.Direction.DESC, "id");
        return questionRepository.findAll(pageable);
    }

    @Override
    public Page<Question> getNewestByTag(long tagId, int uiPageNumber, int pageSize) {
        int validPageSize = getValidPageSize(pageSize);
        long totalRows = questionRepository.count();
        int validPageNumber = getValidPageNumber(uiPageNumber, validPageSize, totalRows);
        Pageable pageable = new PageRequest(validPageNumber, validPageSize, Sort.Direction.DESC, "id");
        return questionRepository.findByTagsId(tagId, pageable);
    }

    private int getValidPageSize(int pageSize) {
        return pageSize <= 0 ? PAGE_SIZE : pageSize;
    }

    int getValidPageNumber(int uiPageNumber, int pageSize, long totalRows) {
        int totalPages = (int) Math.ceil((double) totalRows / pageSize);
        return uiPageNumber < 1 || uiPageNumber > totalPages ? 0 : uiPageNumber - 1;
    }

    @Override
    public List<Question> search(String query) {
        return questionSearchRepository.findByTitleOrDescription(query, query, new PageRequest(0, 9999)).getContent();
    }

    private void validateQuestionStatus(Question questionRegister) {
        if (questionRegister.getStatus() == ItemStatus.CLOSED) {
            throw new IllegalStateException("The question cannot be modified if it is closed");
        }
    }
}
