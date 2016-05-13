package com.nearsoft.questions.service.impl;

import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.repository.AnswerRepository;
import com.nearsoft.questions.repository.QuestionRepository;
import com.nearsoft.questions.repository.search.QuestionSearchRepository;
import com.nearsoft.questions.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionSearchRepository questionSearchRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionSearchRepository questionSearchRepository) {
        this.questionRepository = questionRepository;
        this.questionSearchRepository = questionSearchRepository;
    }

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public void save(Question question) {
        questionRepository.save(question);
    }

    @Override
    public synchronized void updateTotalAnswers(Question question) {
        int total = answerRepository.countByQuestionId(question.getId());
        if (log.isDebugEnabled()) {
            log.debug(String.format("Updating question %d with total answers: %d", question.getId(), total));
        }
        question.setTotalAnswers(total);
        questionRepository.save(question);
    }

    @Override
    public Question get(long id) {
        return questionRepository.findOne(id);
    }

    @Override
    public Page<Question> getUnanswered(int UIPageNumber, int pageSize) {
        int validPageSize = getValidPageSize(pageSize);
        long totalRows = questionRepository.countByAnswersIsNull();
        int validPageNumber = getValidPageNumber(UIPageNumber, validPageSize, totalRows);
        Pageable pageable = new PageRequest(validPageNumber, validPageSize, Sort.Direction.DESC, "id");
        return questionRepository.findByAnswersIsNull(pageable);
    }

    @Override
    public Page<Question> getNewest(int UIPageNumber, int pageSize) {
        int validPageSize = getValidPageSize(pageSize);
        long totalRows = questionRepository.count();
        int validPageNumber = getValidPageNumber(UIPageNumber, validPageSize, totalRows);
        Pageable pageable = new PageRequest(validPageNumber, validPageSize, Sort.Direction.DESC, "id");
        return questionRepository.findAll(pageable);
    }

    @Override
    public Page<Question> getNewestByTag(long tagId, int UIPageNumber, int pageSize) {
        int validPageSize = getValidPageSize(pageSize);
        long totalRows = questionRepository.count();
        int validPageNumber = getValidPageNumber(UIPageNumber, validPageSize, totalRows);
        Pageable pageable = new PageRequest(validPageNumber, validPageSize, Sort.Direction.DESC, "id");
        return questionRepository.findByTagsId(tagId, pageable);
    }

    private int getValidPageSize(int pageSize) {
        return pageSize <= 0 ? PAGE_SIZE : pageSize;
    }

    private int getValidPageNumber(int UIPageNumber, int pageSize, long totalRows) {
        long totalPages = new Double(Math.ceil((double) totalRows / pageSize)).intValue();
        return UIPageNumber < 1 || UIPageNumber > totalPages ? 0 : UIPageNumber - 1;
    }

    @Override
    public List<Question> search(String query) {
        return questionSearchRepository.findByTitleOrDescription(query, query);
    }
}
