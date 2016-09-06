package com.nearsoft.questions.service.impl.deliverer;

import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.Notification;
import com.nearsoft.questions.domain.NotificationType;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.Tag;
import com.nearsoft.questions.domain.TagSubscription;
import com.nearsoft.questions.domain.UserNotification;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.repository.AnswerRepository;
import com.nearsoft.questions.repository.NotificationRepository;
import com.nearsoft.questions.repository.TagsSubscriptionRepository;
import com.nearsoft.questions.repository.UserNotificationRepository;
import com.nearsoft.questions.service.MailSenderService;
import com.nearsoft.questions.service.NotificationDelivererService;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.mail.MessagingException;

@Service
public class NewAnswerNotifierServiceImpl implements NotificationDelivererService {

    private static final String QUESTION_TITLE = "questionTitle";
    private static final String QUESTION_ID = "questionId";
    private static final String USER_NAME_QUESTION = "userNameQuestion";
    private static final String ANSWER_TEXT = "answerText";
    private static final String ANSWER_ID = "answerId";
    private static final String USER_NAME_ANSWER = "userNameAnswer";
    private static final String USER_NAME = "userName";
    private static final String TAGS_LIST = "tagsList";
    private static final String APPLICATION_PATH = "applicationSitePath";

    public static final String ANSWER_ID_PARAM = "com.nsquestions.answer.id";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${com.nsquestions.notification.answer-for-tagged-question:'New answer for tagged question'}")
    private String answerForTaggedQuestion;

    @Value("${com.nsquestions.notification.you-got-an-answer:'You got an answer'}")
    private String youGotAnAnswerMsg;

    @Value("${application.site}")
    private String applicationSitePath;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserNotificationRepository userNotificationRepository;

    @Autowired
    private TagsSubscriptionRepository tagsSubscriptionRepository;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private ParameterReader parameterReader;

    @Override
    public void sendNotification(Map<String, String> parametersMap) {
        Answer answer = answerRepository.findOne(parameterReader.getLong(parametersMap, ANSWER_ID_PARAM));
        Question question = answer.getQuestion();

        notifyQuestionOwner(question, answer);

        notifyInterestedUsersByTags(question, answer);
    }


    private void notifyQuestionOwner(Question question, Answer answer) {
        User user = question.getUser();

        Map<String, String> templateParams = new HashMap<>();

        templateParams.put(QUESTION_TITLE, question.getTitle());
        templateParams.put(ANSWER_TEXT, answer.getDescription());
        templateParams.put(USER_NAME_ANSWER, answer.getUser().getFirstName());
        templateParams.put(USER_NAME, user.getFirstName());
        templateParams.put(ANSWER_ID, answer.getId().toString());
        templateParams.put(QUESTION_ID, question.getId().toString());
        templateParams.put(APPLICATION_PATH, applicationSitePath);
        persistNotification(question, answer, user);

        mailSenderService.sendEmail(NotificationType.ADD_ANSWER, youGotAnAnswerMsg, templateParams, user.getEmail());

    }

    @SuppressWarnings("unchecked")
    private void persistNotification(Question question, Answer answer, User user) {
        Notification notification = new Notification();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("text", answer.getDescription());
        jsonObject.put("questionId", question.getId());
        jsonObject.put("answerId", answer.getId());

        notification.setDescription(jsonObject.toJSONString());
        notification.setType(NotificationType.ADD_ANSWER);

        notificationRepository.save(notification);

        UserNotification userNotification = new UserNotification();

        userNotification.setNotification(notification);
        userNotification.setUser(user);

        userNotificationRepository.save(userNotification);

    }

    private void notifyInterestedUsersByTags(Question question, Answer answer) {
        List<Tag> tags = question.getTags();

        AnswerNotifierTagsByUserConsumer notifierTagsByUser = new AnswerNotifierTagsByUserConsumer(question, answer, this);

        try (Stream<TagSubscription> stream = tagsSubscriptionRepository.findByTagIsInOrderByTagAsc(tags)) {
            stream.forEach(notifierTagsByUser);
        }
    }

    public void sendNotification(Question question, Answer answer, String tag, User user, Notification notification) {

        if (notification.getId() == null) {
            notificationRepository.save(notification);
        }

        Map<String, String> templateParams = new HashMap<>();

        templateParams.put(QUESTION_TITLE, question.getTitle());
        templateParams.put(USER_NAME_QUESTION, question.getUser().getFirstName());
        templateParams.put(ANSWER_TEXT, answer.getDescription());
        templateParams.put(USER_NAME_ANSWER, answer.getUser().getFirstName());
        templateParams.put(TAGS_LIST, tag);
        templateParams.put(USER_NAME, user.getFirstName());

        persistUserNotification(notification, user);

        String mailSubject = MessageFormat.format(answerForTaggedQuestion, tag);

        mailSenderService.sendEmail(NotificationType.ANSWER_FOR_TAGGED_QUESTION, mailSubject, templateParams, user.getEmail());

    }

    private void persistUserNotification(Notification notification, User user) {
        UserNotification userNotification = new UserNotification();

        userNotification.setNotification(notification);
        userNotification.setUser(user);

        userNotificationRepository.save(userNotification);
    }


}
