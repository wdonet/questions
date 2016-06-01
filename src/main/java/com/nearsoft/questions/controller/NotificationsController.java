package com.nearsoft.questions.controller;


import com.nearsoft.questions.domain.Notification;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inbox")
public class NotificationsController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(getClass());

//    @Inject
//    private NotificationService notificationService;

    @Autowired
    private NotificationRepository repository;


    @RequestMapping(method = RequestMethod.GET, value = "/notifications")
    @ResponseBody
    public List<Notification> getAll(Model model, @AuthenticationPrincipal UserDetails activeUser) {
        model.addAttribute("notifications", repository.findByUser(activeUser.getUser()));
        return repository.findByUser(activeUser.getUser());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/notifications/{id}")
    public void delete(@PathVariable Long id) {
        repository.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/notifications/read/{id}")
    public void read(@PathVariable Long id) {
        Notification notification = repository.findOne(id);
        notification.setUiNotified(Boolean.TRUE);
        repository.save(notification);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/notifications/read")
    public void readAll(@AuthenticationPrincipal UserDetails activeUser) {
        List<Notification> notifications = repository.findByUser(activeUser.getUser());
        notifications.forEach(notification -> notification.setUiNotified(true));
    }

}
