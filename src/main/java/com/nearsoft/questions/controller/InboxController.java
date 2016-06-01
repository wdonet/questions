package com.nearsoft.questions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/inbox")
public class InboxController {

    @RequestMapping(method = RequestMethod.GET)
    public String inboxHome() {
        return "inbox";
    }

}
