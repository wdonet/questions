package com.nearsoft.questions.controller;

import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.Tag;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;

@Controller
public class HomeController {

    private final Logger _log = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuestionService _questionService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model) {
        _log.info("-- search view --");
        Question question = new Question().withTitle("Suggestion 1");
        question.setId(1L);
        question.setTotalAnswers(5);

        User user = new User();
        user.setFirstName("Jane");
        user.setLastName("Roe");
        question.setUser(user);

        Tag tag = new Tag("finance");
        tag.setId(1L);
        question.addTag(tag);

        model.addAttribute("questions", Arrays.asList(question));
        return "searchQuestion";
    }

    @RequestMapping(value = "/ask", method = RequestMethod.GET)
    public String ask(Model model) {
        return "askQuestion";
    }

}
