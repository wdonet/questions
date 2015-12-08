package com.nearsoft.questions.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(getClass());


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        log.info("Rendering login view");

        return "login";
    }
}
