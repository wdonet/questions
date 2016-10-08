package com.nearsoft.questions.controller.api;

import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.repository.QuestionRepository;
import com.nearsoft.questions.repository.search.QuestionSearchRepository;
import com.nearsoft.questions.service.ConfigurationService;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RepositoryRestController
@RequestMapping("/api/question/")
public class QuestionApiController {
    private final ConfigurationService configurationService;
    private final PagedResourcesAssembler<Question> assembler;
    private final QuestionSearchRepository searchRepository;
    private final QuestionRepository repository;


    @Autowired
    public QuestionApiController(ConfigurationService configurationService, QuestionSearchRepository searchRepository,
            QuestionRepository repository, PagedResourcesAssembler<Question> assembler) {
        this.configurationService = configurationService;
        this.searchRepository = searchRepository;
        this.repository = repository;
        this.assembler = assembler;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<PagedResources<Resource<Question>>> search(@RequestParam String term, Pageable pageable) {

        Page<Question> results = searchRepository.findByTitleOrDescription(term, term, pageable);
        return new ResponseEntity<>(assembler.toResource(results), HttpStatus.OK);
    }

    @RequestMapping(value = "/unanswered", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<PagedResources<Resource<Question>>> unanswered(Pageable pageable) {

        Page<Question> results = repository.findByAnswersIsNull(pageable);
        return new ResponseEntity<>(assembler.toResource(results), HttpStatus.OK);
    }

    @RequestMapping(value = "/newest", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<PagedResources<Resource<Question>>> newest(Pageable pageable) {
        Sort createdAtDescAndUserChoices = new Sort(Sort.Direction.DESC, "createdAt").and(pageable.getSort());
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                createdAtDescAndUserChoices);
        Page<Question> results = repository.findAll(pageRequest);
        //TODO show only 1 answer
        return new ResponseEntity<>(assembler.toResource(results), HttpStatus.OK);
    }

    /**
     * suggestions returns a map with a single element which key is the string "suggestions" and value is the list of
     * question titles that partial match with the given string. The purpose of this method is to provide suggestions
     * of existing questions when a user is searching answers. The number of suggestions is limited to by the config
     * max_number_autocomplete_suggestions.
     *
     * @param term the string that will be used to search partial matches with question titles.
     * @return a map with a single element which key is the string "suggestions" and value is the list of
     * suggested question titles that partial match with the given string.
     */
    @RequestMapping(value = "/suggestions", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<String>> suggestions(@RequestParam String term) {
        List<Question> questions = searchRepository.findByTitleLike(term);
        questions = limitNumberOfSuggestions(questions);
        List<String> suggestions = questions.stream().map(Question::getTitle).collect(Collectors.toList());
        return Collections.singletonMap("suggestions", suggestions);
    }

    private List<Question> limitNumberOfSuggestions(List<Question> questions) {
        int maxNumberOfSuggestions = configurationService.getInteger("max_number_autocomplete_suggestions", 10);
        if (questions.size() > maxNumberOfSuggestions) {
            questions = questions.subList(0, maxNumberOfSuggestions);
        }
        return questions;
    }
}
