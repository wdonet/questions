package com.nearsoft.questions.service.impl.deliverer;

import com.nearsoft.questions.domain.Notification;
import com.nearsoft.questions.domain.NotificationType;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.Tag;
import com.nearsoft.questions.domain.TagSubscription;
import com.nearsoft.questions.domain.UserNotification;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.repository.NotificationRepository;
import com.nearsoft.questions.repository.QuestionRepository;
import com.nearsoft.questions.repository.TagsSubscriptionRepository;
import com.nearsoft.questions.repository.UserNotificationRepository;
import com.nearsoft.questions.service.MailSenderService;
import com.nearsoft.questions.service.NotificationDelivererService;

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

/**
 * This class uses MessageFormat to format the subject of the notification.
 *
 * The dynamic parameters are set in the following order:
 *
 * {0} = Tags list
 */
@Service
public class NewQuestionNotifierServiceImpl implements NotificationDelivererService {

    private static final String QUESTION_TITLE = "questionTitle";
    private static final String USER_NAME_QUESTION = "userNameQuestion";
    private static final String TAGS_LIST = "tagsList";
    private static final String USER_NAME = "userName";

    public static final String QUESTION_ID_PARAM = "com.nsquestions.question.id";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserNotificationRepository userNotificationRepository;

    @Autowired
    private TagsSubscriptionRepository tagsSubscriptionRepository;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    ParameterReader parameterReader;

    @Value("${com.nsquestions.notification.newquestion.subject:'Question tagged for {0}'}")
    private String subject;

    @Override
    public void sendNotification(Map<String, String> parametersMap) {

        Question question = questionRepository.findOne(parameterReader.getLong(parametersMap, QUESTION_ID_PARAM));

        List<Tag> tags = question.getTags();
        QuestionNotifierTagsByUserConsumer notifierTagsByUser = new QuestionNotifierTagsByUserConsumer(question, this);

        try (Stream<TagSubscription> stream = tagsSubscriptionRepository.findByTagIsInOrderByUserAsc(tags)) {
            stream.forEach(notifierTagsByUser);
        }

        notifierTagsByUser.sendRemainingNotifications();
    }

    public void sendNotification(Question question, String tagsList, User user) {

        if (tagsList.length() == 0 || user == null) {
            return;
        }

        Map<String, String> templateParams = new HashMap<>();

        templateParams.put(QUESTION_TITLE, question.getTitle());
        templateParams.put(USER_NAME_QUESTION, question.getUser().getFirstName());
        templateParams.put(TAGS_LIST, tagsList);
        templateParams.put(USER_NAME, user.getFirstName());

        Notification notification = new Notification();

        String description = MessageFormat.format(subject, tagsList);

        notification.setDescription(description);
        notification.setType(NotificationType.NEW_QUESTION);
        notification.setQuestion(question);

        notificationRepository.save(notification);

        UserNotification userNotification = new UserNotification();

        userNotification.setNotification(notification);
        userNotification.setUser(user);
        userNotification.setUiNotified(false);
        userNotification.setEmailDelivered(false);

        userNotificationRepository.save(userNotification);

        try {
            mailSenderService.sendEmail(NotificationType.NEW_QUESTION, description, templateParams, user.getEmail());
        } catch (MessagingException e) {
            log.error("Can't deliver notification by email", e);
        }
    }

}
