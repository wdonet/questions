package com.nearsoft.questions.service.impl.deliverer;

import com.nearsoft.questions.domain.Notification;
import com.nearsoft.questions.domain.NotificationType;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.repository.NotificationRepository;
import com.nearsoft.questions.repository.QuestionRepository;
import com.nearsoft.questions.repository.UserRepository;
import com.nearsoft.questions.service.MailSenderService;
import com.nearsoft.questions.service.NotificationDelivererService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

@Service
public class QuestionVotedNotifierServiceImpl implements NotificationDelivererService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String QUESTION_ID_PARAM = "com.nsquestions.question.id";

    @Value("${com.nsquestions.notification.question-voted-up.subject:Question voted up}")
    private String subjectQuestionVotedUp;

    @Value("${com.nsquestions.notification.question-voted-down.subject:Question voted down}")
    private String subjectQuestionVotedDown;

    @Value("${com.nsquestions.notification.len-start-of-question:15}")
    private int lenStartOfQuestion;

    public static final String POINTS_PARAM = "com.nsquestions.notification.voted.points";

    private static final int LEN_ELLIPSIS = 3;

    private static final String ELLIPSIS = "...";

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    ParameterReader parameterReader;


    @Override
    public void sendNotification(Map<String, String> parametersMap) {
        int points = parameterReader.getInteger(parametersMap, POINTS_PARAM);
        Question question = questionRepository.findOne(parameterReader.getLong(parametersMap, QUESTION_ID_PARAM));
        String questionText = question.getTitle();
        String startOfQuestion = questionText.length() > lenStartOfQuestion ? questionText.substring(0, lenStartOfQuestion - LEN_ELLIPSIS) + ELLIPSIS : questionText;
        String description = points > 0 ? subjectQuestionVotedUp : subjectQuestionVotedDown;
        NotificationType notificationType = points > 0 ? NotificationType.QUESTION_VOTED_UP : NotificationType.QUESTION_VOTED_DOWN;

        description = MessageFormat.format(description, startOfQuestion);

        User user = question.getUser();

        Map<String, String> templateParams = new HashMap<>();

        templateParams.put("description", description);
        templateParams.put("questionTitle", question.getTitle());
        templateParams.put("votesUp", "" + question.getVotesUp());
        templateParams.put("votesDown", "" + question.getVotesDown());
        templateParams.put("reputation", "" + userRepository.getPointsForUserId(user.getId()));

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
            mailSenderService.sendEmail(notificationType, description, templateParams, user.getEmail());
        } catch (MessagingException e) {
            log.error("Can't deliver notification by email", e);
        }
    }
}
