package com.nearsoft.questions.service.impl.deliverer;

import com.nearsoft.questions.domain.NotificationType;
import com.nearsoft.questions.domain.Question;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rjimenez on 5/31/16.
 */
@Service
public class NewQuestionNotifierServiceImpl implements NotificationDelivererService {

    @Autowired
    MailSenderService mailSenderService;


    @Override
    public void sendNotification(Map<String, String> parametersMap) {
        try {
            Question question = new Question();
            question.setDescription("Question 1");
            List<String> mailList = new ArrayList<>();

            mailSenderService.sendEmail(mailList, "subject", question, NotificationType.ADD);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
