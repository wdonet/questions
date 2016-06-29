package com.nearsoft.questions.service.impl;

import com.nearsoft.questions.domain.NotificationType;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.service.MailSenderService;
import com.nearsoft.questions.service.TemplateGeneratorService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.*;

@Service
public class MailSenderServiceImpl implements MailSenderService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateGeneratorService templateGeneratorService;


    @Override
    @Async
    public void sendEmail(NotificationType notificationType, String subject, Object object, String... mailList) throws MessagingException {
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        try {
            helper.setText(templateGeneratorService.getTemplate(notificationType.getMailTemplateName(), object));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        helper.setTo(mailList);
        helper.setSubject(subject);
        javaMailSender.send(mail);
    }
}
