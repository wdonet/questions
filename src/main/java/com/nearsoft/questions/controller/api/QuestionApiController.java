package com.nearsoft.questions.controller.api;


import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.repository.QuestionRepository;
import com.nearsoft.questions.repository.search.QuestionSearchRepository;
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
    private final QuestionRepository repository;

    private PagedResourcesAssembler<Question> assembler;

    @Autowired
    public QuestionApiController(QuestionSearchRepository searchRepository, QuestionRepository repository, PagedResourcesAssembler<Question> assembler) {
        this.searchRepository = searchRepository;
        this.repository = repository;
        this.assembler = assembler;
    }

    @RequestMapping(value = "/questions/search", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpEntity<PagedResources<Resource<Question>>> search(@RequestParam String term, Pageable pageable) {

        Page<Question> results = searchRepository.findByTitleOrDescription(term, term, pageable);
        return new ResponseEntity<>(assembler.toResource(results), HttpStatus.OK);

    }

    @RequestMapping(value = "/questions/unanswered", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpEntity<PagedResources<Resource<Question>>> unanswered(Pageable pageable) {

        Page<Question> results = repository.findByAnswersIsNull(pageable);
        return new ResponseEntity<>(assembler.toResource(results), HttpStatus.OK);

    }

    @RequestMapping(value = "/questions/newest", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpEntity<PagedResources<Resource<Question>>> newest(Pageable pageable) {

        Page<Question> results = repository.findAll(new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                new Sort(Sort.Direction.DESC, "createdAt")));
        return new ResponseEntity<>(assembler.toResource(results), HttpStatus.OK);

    }
}
