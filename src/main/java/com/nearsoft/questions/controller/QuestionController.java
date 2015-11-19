package com.nearsoft.questions.controller;

import java.util.Arrays;
import java.util.List;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/question")
public class QuestionController {

    private final Logger _log = LoggerFactory.getLogger(getClass());

    @Autowired
    QuestionService _questionService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String get(@PathVariable long id, Model model) {
        _log.info("question with id " + id);
        model.addAttribute(_questionService.get(id));
        return "show1Question";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public List<Question> search(@RequestParam String query) {
        _log.info("query " + query);
        return _questionService.search(query);
    }

}
