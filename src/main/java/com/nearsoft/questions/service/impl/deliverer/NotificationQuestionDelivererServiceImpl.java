package com.nearsoft.questions.service.impl.deliverer;

import com.nearsoft.questions.service.MailSenderService;
import com.nearsoft.questions.service.NotificationDelivererService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * Created by rjimenez on 5/31/16.
 */
@Service
public class NotificationQuestionDelivererServiceImpl implements NotificationDelivererService {

    @Autowired
    MailSenderService mailSenderService;


    @Override
    public void sendNotification(Map<String, String> parametersMap) {
        try {
            mailSenderService.sendEmail("rjimenez@nearsoft.com", "subject", "Text");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
