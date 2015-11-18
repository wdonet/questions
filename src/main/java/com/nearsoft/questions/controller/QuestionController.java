package com.nearsoft.questions.controller;

import java.util.List;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService _questionService;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<Question> search(@RequestParam String query) {
        return _questionService.search(query);
    }

}
