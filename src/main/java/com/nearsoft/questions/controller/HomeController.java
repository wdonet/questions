package com.nearsoft.questions.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private final Logger _log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/home")
    public String home() {
        _log.info("-- showing home --");
        return "<h3>NS Questions!</h3>";
    }

}
