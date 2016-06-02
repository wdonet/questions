package com.nearsoft.questions.controller.api;


import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.repository.QuestionRepository;
import com.nearsoft.questions.repository.search.QuestionSearchRepository;
import com.nearsoft.questions.service.QuestionService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class QuestionApiController {

    private final QuestionSearchRepository searchRepository;
    private final QuestionService questionService;
    private final QuestionRepository repository;

    private PagedResourcesAssembler<Question> assembler;

    private final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    public QuestionApiController(QuestionSearchRepository searchRepository, QuestionService questionService, QuestionRepository repository,
        PagedResourcesAssembler<Question> assembler) {
        this.searchRepository = searchRepository;
        this.questionService = questionService;
        this.repository = repository;
        this.assembler = assembler;
    }

    @RequestMapping(value = "/questions/search", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<PagedResources<Resource<Question>>> search(@RequestParam String term, Pageable pageable) {

        Page<Question> results = searchRepository.findByTitleOrDescription(term, term, pageable);
        return new ResponseEntity<>(assembler.toResource(results), HttpStatus.OK);

    }

    @RequestMapping(value = "/questions/unanswered", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<PagedResources<Resource<Question>>> unanswered(Pageable pageable) {

        Page<Question> results = repository.findByAnswersIsNull(pageable);
        return new ResponseEntity<>(assembler.toResource(results), HttpStatus.OK);

    }

    @RequestMapping(value = "/questions/newest", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<PagedResources<Resource<Question>>> newest(Pageable pageable) {
        Sort createdAtDescAndUserChoices = new Sort(Sort.Direction.DESC, "createdAt").and(pageable.getSort());;
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), createdAtDescAndUserChoices);
        Page<Question> results = repository.findAll(pageRequest);

        log.info("Show onlyOneAnswer ? " + questionService.isOnlyOneAnswer());
        results.forEach(one -> {
            boolean isMoreThanOneAnswer = false;
            try {
                isMoreThanOneAnswer = CollectionUtils.size(one.getAnswers()) > 1;
            } catch (IllegalArgumentException ignored) {
                // when this happens there's no answers (null) and isMoreThanOneAnswer remains false
            }
            if (isMoreThanOneAnswer && questionService.isOnlyOneAnswer()) {
                one.setTotalAnswers(1);
            }
        });
        return new ResponseEntity<>(assembler.toResource(results), HttpStatus.OK);
    }
}
