package com.nearsoft.questions.controller.api;


import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.repository.QuestionRepository;
import com.nearsoft.questions.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.apache.solr.core.SolrConfig.log;

@RepositoryRestController
public class QuestionController {

    private final QuestionRepository repository;
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionRepository repository, QuestionService questionService) {
        this.repository = repository;
        this.questionService = questionService;
    }

    @RequestMapping(value = "/questions/search", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<?> search(@RequestParam String term) {
        log.debug("Query : " + term);
        List<Question> results = questionService.search(term);
        Resources<Question> resources = new Resources<>(results);

        return ResponseEntity.ok(resources);

    }

}
