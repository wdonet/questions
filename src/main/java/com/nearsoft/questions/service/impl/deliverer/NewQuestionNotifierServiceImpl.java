package com.nearsoft.questions.service.impl.deliverer;

import com.nearsoft.questions.domain.*;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.repository.NotificationRepository;
import com.nearsoft.questions.repository.QuestionRepository;
import com.nearsoft.questions.repository.TagsSubscriptionRepository;
import com.nearsoft.questions.service.MailSenderService;
import com.nearsoft.questions.service.NotificationDelivererService;
import com.nearsoft.questions.service.TagsSubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class NewQuestionNotifierServiceImpl implements NotificationDelivererService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String QUESTION_ID_PARAM = "com.nsquestions.question.id";

    private static final String NEW_QUESTION_MSG = "NEW QUESTION: ";

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private TagsSubscriptionRepository tagsSubscriptionRepository;

    @Autowired
    private TagsSubscriptionService tagsSubscriptionService;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    ParameterReader parameterReader;

    @Value("${com.nsquestions.notification.newquestion.subject:New question}")
    private String subject;

    @Override
    public void sendNotification(Map<String, String> parametersMap) {
        Question question = questionRepository.findOne(parameterReader.getLong(parametersMap, QUESTION_ID_PARAM));

        List<Tag> tags = question.getTags();

        List<User> tagSubscriptions = tagsSubscriptionService.findByTagsIsIn(tags);

        Map<String, String> templateParams = new HashMap<>();

        String tagList = buildTagsListParam(tags);

        templateParams.put("tagList", tagList);
        templateParams.put("description", NEW_QUESTION_MSG + question.getDescription());

        for (User user : tagSubscriptions) {
            Notification notification = new Notification();

            notification.setDescription(NEW_QUESTION_MSG + question.getDescription());
            notification.setType(NotificationType.ADD);
            notification.setUser(user);
            notification.setUiNotified(false);
            notification.setEmailDelivered(false);
            notification.setQuestion(question);

            templateParams.put("userName", user.getFirstName());

            notificationRepository.save(notification);

            try {
                mailSenderService.sendEmail(NotificationType.ADD, subject, templateParams, user.getEmail());
            } catch (MessagingException e) {
                log.error("Can't deliver notification by email", e);
            }
        }


    }

    private String buildTagsListParam(List<Tag> tags) {
        StringBuilder tagsList = new StringBuilder();

        tagsList.append("[");
        for (Tag tag : tags) {
            if (tagsList.length() > 1) {
                tagsList.append(",");
            }
            tagsList.append(tag.getName());
        }
        tagsList.append("]");

        return tagsList.toString();

    }
}
