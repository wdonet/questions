package com.nearsoft.questions.controller;


import com.nearsoft.questions.domain.Notification;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import java.util.List;

@Controller
@RequestMapping("/inbox")
public class NotificationsController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(getClass());

//    @Inject
//    private NotificationService notificationService;

    @Inject
    private NotificationRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Notification> getAll(Model model, @AuthenticationPrincipal UserDetails activeUser) {
        model.addAttribute("notifications", repository.findByUser(activeUser.getUser()));
        return repository.findByUser(activeUser.getUser());
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public void delete(@PathVariable Long id) {
        repository.delete(id);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public void read(@PathVariable Long id) {
        repository.delete(id);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/")
    public void readAll(@AuthenticationPrincipal UserDetails activeUser) {
        List<Notification> notifications = repository.findByUser(activeUser.getUser());
        notifications.forEach(notification -> notification.setUiNotified(true));
    }

}
