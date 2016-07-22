package com.nearsoft.questions.service.impl.deliverer;

import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.Notification;
import com.nearsoft.questions.domain.NotificationType;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.repository.AnswerRepository;
import com.nearsoft.questions.repository.NotificationRepository;
import com.nearsoft.questions.service.MailSenderService;
import com.nearsoft.questions.service.NotificationDelivererService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

@Service
public class AnswerVotedNotifierServiceImpl implements NotificationDelivererService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String ANSWER_ID_PARAM = "com.nsquestions.answer.id";

    public static final String DESCRIPTION_PARAM = "com.nsquestions.notification.description";
    public static final String SUBJECT_PARAM = "com.nsquestions.notification.subject";
    public static final String POINTS_PARAM = "com.nsquestions.notification.voted.points";

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    ParameterReader parameterReader;


    @Override
    public void sendNotification(Map<String, String> parametersMap) {
        int points = parameterReader.getInteger(parametersMap, POINTS_PARAM);
        String description = parameterReader.getString(parametersMap, DESCRIPTION_PARAM);
        String subject = parameterReader.getString(parametersMap, SUBJECT_PARAM);
        Answer answer = answerRepository.findOne(parameterReader.getLong(parametersMap, ANSWER_ID_PARAM));
        NotificationType notificationType = points > 0 ? NotificationType.ANSWER_VOTED_UP : NotificationType.ANSWER_VOTED_DOWN;
        Question question = answer.getQuestion();

        User user = answer.getUser();

        Map<String, String> templateParams = new HashMap<>();

        templateParams.put("description", description);
        templateParams.put("points", parameterReader.getString(parametersMap, POINTS_PARAM));

        Notification notification = new Notification();

        notification.setDescription(description);
        notification.setType(notificationType);
        notification.setUser(user);
        notification.setUiNotified(false);
        notification.setEmailDelivered(false);
        notification.setQuestion(question);

        templateParams.put("userName", user.getFirstName());

        notificationRepository.save(notification);

        try {
            mailSenderService.sendEmail(notificationType, subject, templateParams, user.getEmail());
        } catch (MessagingException e) {
            log.error("Can't deliver notification by email", e);
        }
    }
}
