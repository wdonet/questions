package com.nearsoft.questions.controller;

import com.nearsoft.questions.domain.config.ConfigurationEnum;
import com.nearsoft.questions.service.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ConfigurationService configurationService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String publicUser(Model model) {
        model.addAttribute("headerText", configurationService.getString(ConfigurationEnum.INDEX_HEADER.getConfigName()));
        return "public";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/customizedHome", method = RequestMethod.GET)
    public String customizedHome() {
        return configurationService.getString(ConfigurationEnum.INDEX_PAGE.getConfigName());
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
