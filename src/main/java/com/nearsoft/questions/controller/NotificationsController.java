package com.nearsoft.questions.controller;


import com.nearsoft.questions.domain.UserNotification;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.domain.dto.NotificationViewElement;
import com.nearsoft.questions.repository.NotificationViewElementRepository;
import com.nearsoft.questions.repository.UserNotificationRepository;

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

import javax.transaction.Transactional;

@RestController
@RequestMapping("/inbox")
public class NotificationsController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserNotificationRepository userNotificationRepository;

    @Autowired
    private NotificationViewElementRepository notificationViewElementRepository;


    @RequestMapping(method = RequestMethod.GET, value = "/notifications")
    @ResponseBody
    public List<NotificationViewElement> getAll(Model model, @AuthenticationPrincipal UserDetails activeUser) {
        List<NotificationViewElement> notifications = notificationViewElementRepository.getNotificationsForView(activeUser.getUser());
        model.addAttribute("notifications", notifications);
        return notifications;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/notifications/{ids}")
    public void delete(@PathVariable Long[] ids, @AuthenticationPrincipal UserDetails activeUser) {
        userNotificationRepository.deleteByUserAndIdIsIn(activeUser.getUser(), ids);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/notifications/read/{id}")
    @Transactional
    public void read(@PathVariable Long id, @AuthenticationPrincipal UserDetails activeUser) {
        User user = activeUser.getUser();

        userNotificationRepository.markAsRead(id, user);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/notifications/read")
    @Transactional
    public void readAll(@AuthenticationPrincipal UserDetails activeUser) {
        userNotificationRepository.markAllAsRead(activeUser.getUser());
    }

}
