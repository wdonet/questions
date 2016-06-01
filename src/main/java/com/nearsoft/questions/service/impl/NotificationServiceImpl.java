package com.nearsoft.questions.service.impl;

import com.nearsoft.questions.service.NotificationDelivererService;
import com.nearsoft.questions.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Created by rjimenez on 5/31/16.
 */
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public NotificationDelivererService sendNotification(Class<? extends NotificationDelivererService> notificationDeliverer) {
        return applicationContext.getBean(notificationDeliverer);
    }
}
