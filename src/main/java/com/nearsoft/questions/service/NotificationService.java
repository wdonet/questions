package com.nearsoft.questions.service;

/**
 * Created by rjimenez on 5/31/16.
 */
public interface NotificationService {
    public NotificationDelivererService sendNotification(Class<? extends NotificationDelivererService> notificationDeliverer);
}
