package com.nearsoft.questions.controller;

import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/inbox")
public class InboxController {

    @Autowired
    private NotificationRepository notificationRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String inboxHome(Model model, @AuthenticationPrincipal UserDetails activeUser) {
        model.addAttribute("notifications", notificationRepository.findByUserOrderByIdDesc(activeUser.getUser()));
        return "inbox";
    }

}
