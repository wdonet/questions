package com.nearsoft.questions.service.impl.deliverer;

import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.Notification;
import com.nearsoft.questions.domain.NotificationType;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.UserNotification;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.repository.AnswerRepository;
import com.nearsoft.questions.repository.NotificationRepository;
import com.nearsoft.questions.repository.UserNotificationRepository;
import com.nearsoft.questions.repository.UserRepository;
import com.nearsoft.questions.service.MailSenderService;
import com.nearsoft.questions.service.NotificationDelivererService;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

@Service
public class AcceptedAnswerNotifierServiceImpl implements NotificationDelivererService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String ANSWER_ID_PARAM = "com.nsquestions.answer.id";

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserNotificationRepository userNotificationRepository;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private ParameterReader parameterReader;

    @Value("${com.nsquestions.notification.answer-accepted-subjet:'Answer accepted'}")
    private String answerAcceptedSubject;

    @Override
    public void sendNotification(Map<String, String> parametersMap) {
        Answer answer = answerRepository.findOne(parameterReader.getLong(parametersMap, ANSWER_ID_PARAM));
        Question question = answer.getQuestion();

        User user = answer.getUser();

        Map<String, String> templateParams = new HashMap<>();

        templateParams.put("questionTitle", question.getTitle());
        templateParams.put("answerText", answer.getDescription());
        templateParams.put("reputation", "" + userRepository.getPointsForUserId(user.getId()));
        templateParams.put("userName", user.getFirstName());

        persistNotification(question, answer, user);

        try {
            mailSenderService.sendEmail(NotificationType.ANSWER_ACCEPTED, answerAcceptedSubject, templateParams, user.getEmail());
        } catch (MessagingException e) {
            log.error("Can't deliver notification by email", e);
        }
    }

    @SuppressWarnings("unchecked")
    private void persistNotification(Question question, Answer answer, User user) {
        Notification notification = new Notification();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("text", answer.getDescription());
        jsonObject.put("questionId", question.getId());
        jsonObject.put("answerId", answer.getId());

        notification.setDescription(jsonObject.toJSONString());
        notification.setType(NotificationType.ANSWER_ACCEPTED);

        notificationRepository.save(notification);

        UserNotification userNotification = new UserNotification();

        userNotification.setNotification(notification);
        userNotification.setUser(user);

        userNotificationRepository.save(userNotification);
    }
}
