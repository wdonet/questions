package com.nearsoft.questions.controller;


import com.nearsoft.questions.domain.Notification;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.repository.NotificationRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/inbox")
public class NotificationsController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private NotificationRepository repository;


    @RequestMapping(method = RequestMethod.GET, value = "/notifications")
    @ResponseBody
    public List<Notification> getAll(Model model, @AuthenticationPrincipal UserDetails activeUser) {
        List<Notification> notifications = repository.findByUserOrderByIdDesc(activeUser.getUser());
        model.addAttribute("notifications", notifications);
        return notifications;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/notifications/{ids}")
    public void delete(@PathVariable Long[] ids, @AuthenticationPrincipal UserDetails activeUser) {
        repository.deleteByUserAndIdIsIn(activeUser.getUser(), ids);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/notifications/read/{id}")
    public void read(@PathVariable Long id) {
        Notification notification = repository.findOne(id);
        notification.setUiNotified(Boolean.TRUE);
        repository.save(notification);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/notifications/read")
    public void readAll(@AuthenticationPrincipal UserDetails activeUser) {
        List<Notification> notifications = repository.findByUserOrderByIdDesc(activeUser.getUser());
        notifications.forEach(notification -> notification.setUiNotified(true));
    }

}
