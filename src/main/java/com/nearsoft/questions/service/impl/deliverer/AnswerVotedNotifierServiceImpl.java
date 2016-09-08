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
public class AnswerVotedNotifierServiceImpl implements NotificationDelivererService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String ANSWER_ID_PARAM = "com.nsquestions.answer.id";

    @Value("${com.nsquestions.notification.answer-voted-up.subject:Answer voted up}")
    private String subjectAnswerVotedUp;

    @Value("${com.nsquestions.notification.answer-voted-down.subject:Answer voted down}")
    private String subjectAnswerVotedDown;

    public static final String POINTS_PARAM = "com.nsquestions.notification.voted.points";

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserNotificationRepository userNotificationRepository;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    ParameterReader parameterReader;


    @Override
    public void sendNotification(Map<String, String> parametersMap) {
        int points = parameterReader.getInteger(parametersMap, POINTS_PARAM);
        Answer answer = answerRepository.findOne(parameterReader.getLong(parametersMap, ANSWER_ID_PARAM));
        String subject = points > 0 ? subjectAnswerVotedUp : subjectAnswerVotedDown;

        NotificationType notificationType = points > 0 ? NotificationType.ANSWER_VOTED_UP : NotificationType.ANSWER_VOTED_DOWN;
        Question question = answer.getQuestion();

        User user = answer.getUser();

        Map<String, String> templateParams = new HashMap<>();

        templateParams.put("questionTitle", question.getTitle());
        templateParams.put("answerText", answer.getDescription());
        templateParams.put("votesUp", "" + answer.getVotesUp());
        templateParams.put("votesDown", "" + answer.getVotesDown());
        templateParams.put("reputation", "" + userRepository.getPointsForUserId(user.getId()));
        templateParams.put("userName", user.getFirstName());

        persistNotification(question, answer, notificationType, user);

        mailSenderService.sendEmail(notificationType, subject, templateParams, user.getEmail());

    }

    @SuppressWarnings("unchecked")
    private void persistNotification(Question question, Answer answer, NotificationType notificationType, User user) {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("text", answer.getDescription());
        jsonObject.put("questionId", question.getId());
        jsonObject.put("answerId", answer.getId());

        Notification notification = new Notification();

        notification.setDescription(jsonObject.toJSONString());
        notification.setType(notificationType);

        notificationRepository.save(notification);

        UserNotification userNotification = new UserNotification();

        userNotification.setUser(user);
        userNotification.setNotification(notification);

        userNotificationRepository.save(userNotification);
    }
}
