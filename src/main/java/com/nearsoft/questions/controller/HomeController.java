package com.nearsoft.questions.controller;

import java.util.Arrays;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.Tag;
import com.nearsoft.questions.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    private final Logger _log = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuestionService _questionService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String search(Model model) {
        _log.info("-- search view --");
        Question question = new Question().withTitle("Suggestion 1");
        question.setId(87L);
        question.setTotalAnswers(5);
        question.addTag(new Tag("finance"));
        question.addTag(new Tag("important"));
        model.addAttribute("questions", Arrays.asList(question));
        return "searchQuestion";
    }

    @RequestMapping(value = "/ask", method = RequestMethod.GET)
    public String ask(Model model) {
        return "askQuestion";
    }

}
