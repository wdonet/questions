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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.mail.MessagingException;

@Service
public class NewAnswerNotifierServiceImpl implements NotificationDelivererService {

    private static final String QUESTION_TITLE = "questionTitle";
    private static final String USER_NAME_QUESTION = "userNameQuestion";
    private static final String ANSWER_TEXT = "answerText";
    private static final String USER_NAME_ANSWER = "userNameAnswer";
    private static final String DESCRIPTION = "description";
    private static final String USER_NAME = "userName";
    private static final String TAGS_LIST = "tagsList";

    public static final String ANSWER_ID_PARAM = "com.nsquestions.answer.id";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${com.nsquestions.notification.answer-for-tagged-question:'New answer for tagged question'}")
    private String answerForTaggedQuestion;

    @Value("${com.nsquestions.notification.you-got-an-answer:'You got an answer'}")
    private String youGotAnAnswerMsg;

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
        String description = youGotAnAnswerMsg;
        User user = question.getUser();

        Map<String, String> templateParams = new HashMap<>();

        templateParams.put(DESCRIPTION, description);
        templateParams.put(QUESTION_TITLE, question.getTitle());
        templateParams.put(ANSWER_TEXT, answer.getDescription());
        templateParams.put(USER_NAME_ANSWER, answer.getUser().getFirstName());
        templateParams.put(USER_NAME, user.getFirstName());

        Notification notification = new Notification();

        notification.setDescription(description);
        notification.setType(NotificationType.ADD_ANSWER);
        notification.setQuestion(question);

        notificationRepository.save(notification);

        UserNotification userNotification = new UserNotification();

        userNotification.setNotification(notification);
        userNotification.setUser(user);
        userNotification.setUiNotified(false);
        userNotification.setEmailDelivered(false);

        userNotificationRepository.save(userNotification);

        try {
            mailSenderService.sendEmail(NotificationType.ADD_ANSWER, description, templateParams, user.getEmail());
        } catch (MessagingException e) {
            log.error("Can't deliver notification by email", e);
        }
    }

    private void notifyInterestedUsersByTags(Question question, Answer answer) {
        List<Tag> tags = question.getTags();

        Notification notification = new Notification();

        String description = answerForTaggedQuestion;

        notification.setDescription(description);
        notification.setType(NotificationType.ANSWER_FOR_TAGGED_QUESTION);
        notification.setQuestion(question);

        notificationRepository.save(notification);

        AnswerNotifierTagsByUserConsumer notifierTagsByUser = new AnswerNotifierTagsByUserConsumer(question, answer, this, notification);

        try (Stream<TagSubscription> stream = tagsSubscriptionRepository.findByTagIsInOrderByUserAsc(tags)) {
            stream.forEach(notifierTagsByUser);
        }

        notifierTagsByUser.sendRemainingNotifications();
    }

    public void sendNotification(Question question, Answer answer, String tagsList, User user, Notification notification) {

        if (tagsList.length() == 0 || user == null) {
            return;
        }

        Map<String, String> templateParams = new HashMap<>();

        templateParams.put(QUESTION_TITLE, question.getTitle());
        templateParams.put(USER_NAME_QUESTION, question.getUser().getFirstName());
        templateParams.put(ANSWER_TEXT, answer.getDescription());
        templateParams.put(USER_NAME_ANSWER, answer.getUser().getFirstName());
        templateParams.put(TAGS_LIST, tagsList);
        templateParams.put(USER_NAME, user.getFirstName());

        UserNotification userNotification = new UserNotification();

        String description = answerForTaggedQuestion;

        userNotification.setNotification(notification);
        userNotification.setUser(user);
        userNotification.setUiNotified(false);
        userNotification.setEmailDelivered(false);

        userNotificationRepository.save(userNotification);

        try {
            mailSenderService.sendEmail(NotificationType.ANSWER_FOR_TAGGED_QUESTION, description, templateParams, user.getEmail());
        } catch (MessagingException e) {
            log.error("Can't deliver notification by email", e);
        }
    }


}
