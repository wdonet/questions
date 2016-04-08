package com.nearsoft.questions.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search() {
        log.info("-- search view --");
        return "searchQuestion";
    }

    @RequestMapping(value = "/ask", method = RequestMethod.GET)
    public String ask() {
        return "askQuestion";
    }

}
