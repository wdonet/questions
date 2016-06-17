package com.nearsoft.questions.service.impl;

import java.time.ZonedDateTime;
import java.util.List;
import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.RuleName;
import com.nearsoft.questions.domain.RuleQuestionTransaction;
import com.nearsoft.questions.repository.QuestionRepository;
import com.nearsoft.questions.repository.RuleQuestionTransactionRepository;
import com.nearsoft.questions.repository.RuleRepository;
import com.nearsoft.questions.repository.search.QuestionSearchRepository;
import com.nearsoft.questions.service.NotificationDelivererService;
import com.nearsoft.questions.service.NotificationService;
import com.nearsoft.questions.service.QuestionService;
import com.nearsoft.questions.service.impl.deliverer.NewQuestionNotifierServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.HashMapChangeSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final QuestionSearchRepository questionSearchRepository;

    @Autowired
    private RuleQuestionTransactionRepository _ruleQuestionTransactionRepository;

    @Autowired
    private RuleRepository _ruleRepository;

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
    public void save(Question question) {

        questionRepository.save(question);

        NotificationDelivererService delivererService = notificationService.getDelivererInstance(NewQuestionNotifierServiceImpl.class);
        Map<String, String> notificationSettings = new HashMap<>();

        notificationSettings.put(NewQuestionNotifierServiceImpl.QUESTION_ID_PARAM, "" + question.getId());

        delivererService.sendNotification(notificationSettings);

        int points = _ruleRepository.findFirstByRuleName(RuleName.NEW_QUESTION).getPoints();
        RuleQuestionTransaction ruleQuestionTransaction = new RuleQuestionTransaction();
        ruleQuestionTransaction.setCreatedAt(ZonedDateTime.now());
        ruleQuestionTransaction.setPoints(points);
        ruleQuestionTransaction.setQuestionId(question.getId());
        ruleQuestionTransaction.setRuleName(RuleName.NEW_QUESTION);

        _ruleQuestionTransactionRepository.save(ruleQuestionTransaction);
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
}
