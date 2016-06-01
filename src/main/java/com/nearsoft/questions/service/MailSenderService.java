package com.nearsoft.questions.service;

import javax.mail.MessagingException;

/**
 * Created by rjimenez on 6/1/16.
 */
public interface MailSenderService {
    void sendEmail(String sendTo, String subject, String text) throws MessagingException;
}
