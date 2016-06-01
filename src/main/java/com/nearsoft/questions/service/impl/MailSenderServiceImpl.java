package com.nearsoft.questions.service.impl;

import com.nearsoft.questions.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by rjimenez on 6/1/16.
 */
@Service
public class MailSenderServiceImpl implements MailSenderService {

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String sendTo, String subject, String text) throws MessagingException {
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setText(text);
        helper.setTo(sendTo);
        helper.setSubject(subject);
        javaMailSender.send(mail);

    }
}
