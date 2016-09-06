package com.nearsoft.questions.service;

import com.nearsoft.questions.domain.NotificationType;

/**
 * Created by rjimenez on 6/1/16.
 */
public interface MailSenderService {
    void sendEmail(NotificationType notificationType, String subject, Object object, String... mailList);
}
