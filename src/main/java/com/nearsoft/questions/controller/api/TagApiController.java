package com.nearsoft.questions.controller.api;


import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
@RequestMapping("/api/tag/")
public class TagApiController {

    private QuestionRepository questionRepository;
    private PagedResourcesAssembler<Question> assembler;


    @Autowired
    public TagApiController(QuestionRepository questionRepository, PagedResourcesAssembler<Question> assembler) {
        this.questionRepository = questionRepository;
        this.assembler = assembler;
    }

    @RequestMapping(value = "/tags/{id}/questions", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpEntity<PagedResources<Resource<Question>>> newest(@PathVariable Long id, Pageable pageable) {

        Page<Question> results = questionRepository.findByTagsId(id, pageable);
        return new ResponseEntity<>(assembler.toResource(results), HttpStatus.OK);

    }
}
