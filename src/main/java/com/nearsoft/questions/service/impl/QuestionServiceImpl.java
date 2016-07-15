package com.nearsoft.questions.service.impl;

import java.time.ZonedDateTime;
import java.util.List;

import com.nearsoft.questions.domain.*;
import com.nearsoft.questions.repository.QuestionRepository;
import com.nearsoft.questions.repository.RuleQuestionTransactionRepository;
import com.nearsoft.questions.repository.RuleRepository;
import com.nearsoft.questions.repository.search.QuestionSearchRepository;
import com.nearsoft.questions.service.NotificationDelivererService;
import com.nearsoft.questions.service.NotificationService;
import com.nearsoft.questions.service.QuestionService;
import com.nearsoft.questions.service.impl.deliverer.NewQuestionNotifierServiceImpl;
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
import java.util.Map;


@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final QuestionSearchRepository questionSearchRepository;

    @Autowired
    private RuleQuestionTransactionRepository ruleQuestionTransactionRepository;

    @Autowired
    private RuleRepository ruleRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${questions.onlyOneAnswer}")
    private Boolean onlyOneAnswer;

    @Autowired
    NotificationService notificationService;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionSearchRepository questionSearchRepository) {
        this.questionRepository = questionRepository;
        this.questionSearchRepository = questionSearchRepository;
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

        savePointsByRuleName(question, RuleName.NEW_QUESTION);
    }

    @Override
    public void downVote(Long questionId) {
        Question question = get(questionId);
        if (question != null) {
            question.setVotesDown(question.getVotesDown() + 1);
            questionRepository.save(question);
            savePointsByRuleName(question, RuleName.VOTED_DOWN_QUESTION);
        }
        else {
            log.warn("Question " + questionId + " not found when voting down");
        }
    }

    @Override
    public void upVote(Long questionId) {
        Question question = get(questionId);
        if (question != null) {
            question.setVotesUp(question.getVotesUp() + 1);
            questionRepository.save(question);
            savePointsByRuleName(question, RuleName.VOTED_UP_QUESTION);
        }
        else {
            log.warn("Question " + questionId + " not found when voting up");
        }
    }

    private void savePointsByRuleName(Question question, RuleName ruleName) {
        int points = ruleRepository.findFirstByRuleName(ruleName).getPoints();
        RuleQuestionTransaction ruleQuestionTransaction = new RuleQuestionTransaction();
        ruleQuestionTransaction.setCreatedAt(ZonedDateTime.now());
        ruleQuestionTransaction.setPoints(points);
        ruleQuestionTransaction.setQuestionId(question.getId());
        ruleQuestionTransaction.setRuleName(ruleName);
        ruleQuestionTransactionRepository.save(ruleQuestionTransaction);
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
        if (questionRegister.getStatus() == ItemStatus.CLOSED){
            throw new IllegalStateException("The question cannot be modified if it is closed");
        }
    }
}
