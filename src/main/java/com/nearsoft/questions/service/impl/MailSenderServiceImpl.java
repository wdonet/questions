package com.nearsoft.questions.service.impl;

import com.nearsoft.questions.domain.NotificationType;
import com.nearsoft.questions.service.MailSenderService;
import com.nearsoft.questions.service.TemplateGeneratorService;

import freemarker.template.TemplateException;

import org.apache.commons.lang.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSenderServiceImpl implements MailSenderService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateGeneratorService templateGeneratorService;


    @Override
    @Async
    public void sendEmail(NotificationType notificationType, String subject, Object object, String... mailList) {
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(mailList);
            helper.setSubject(subject);
            mail.setContent(templateGeneratorService.getTemplate(notificationType.getMailTemplateName(), object), MediaType.TEXT_HTML_VALUE + "; charset=" + CharEncoding.UTF_8);
            javaMailSender.send(mail);
        } catch (IOException | TemplateException | MessagingException e) {
            e.printStackTrace();
        }
    }
}
