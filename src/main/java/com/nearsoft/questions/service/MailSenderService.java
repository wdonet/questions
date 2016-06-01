package com.nearsoft.questions.service;

import com.nearsoft.questions.domain.NotificationType;

import javax.mail.MessagingException;
import java.util.List;

/**
 * Created by rjimenez on 6/1/16.
 */
public interface MailSenderService {
    void sendEmail(List<String> mailList, String subject, Object object, NotificationType notificationType) throws MessagingException;
}
