package com.nearsoft.questions.service.impl.deliverer;

import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.Notification;
import com.nearsoft.questions.domain.NotificationType;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.Tag;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.repository.AnswerRepository;
import com.nearsoft.questions.repository.NotificationRepository;
import com.nearsoft.questions.repository.TagsSubscriptionRepository;
import com.nearsoft.questions.service.MailSenderService;
import com.nearsoft.questions.service.NotificationDelivererService;
import com.nearsoft.questions.service.TagsSubscriptionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

@Service
public class NewAnswerNotifierServiceImpl implements NotificationDelivererService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String ANSWER_ID_PARAM = "com.nsquestions.answer.id";

    private static final String MSG_TAGGED = "This answer might be interesting to you!";

    private static final String QUESTION_ANSWERED_MSG = "Your question has been answered";

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private NotificationRepository notificationRepository;

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
        Answer answer = answerRepository.findOne(parameterReader.getLong(parametersMap, ANSWER_ID_PARAM));
        Question question = answer.getQuestion();

        notifyQuestionOwner(question, answer);

        notifyInterestedUsersByTags(question, answer);

    }


    private void notifyQuestionOwner(Question question, Answer answer) {
        String description = QUESTION_ANSWERED_MSG;
        User user = question.getUser();

        Map<String, String> templateParams = new HashMap<>();

        templateParams.put("description", description);
        templateParams.put("questionTitle", question.getTitle());
        templateParams.put("answerText", answer.getDescription());
        templateParams.put("userNameAnswer", answer.getUser().getFirstName());

        Notification notification = new Notification();

        notification.setDescription(description);
        notification.setType(NotificationType.ADD_ANSWER);
        notification.setUser(user);
        notification.setUiNotified(false);
        notification.setEmailDelivered(false);
        notification.setQuestion(question);

        templateParams.put("userName", user.getFirstName());

        notificationRepository.save(notification);

        try {
            mailSenderService.sendEmail(NotificationType.ADD_ANSWER, subject, templateParams, user.getEmail());
        } catch (MessagingException e) {
            log.error("Can't deliver notification by email", e);
        }
    }

    private void notifyInterestedUsersByTags(Question question, Answer answer) {
        List<Tag> tags = question.getTags();

        List<User> tagSubscriptions = tagsSubscriptionService.findByTagsIsIn(tags);

        Map<String, String> templateParams = new HashMap<>();

        String tagList = buildTagsListParam(tags);

        templateParams.put("tagList", tagList);
        templateParams.put("questionTitle", question.getTitle());
        templateParams.put("answerText", answer.getDescription());
        templateParams.put("userNameAnswer", answer.getUser().getFirstName());

        for (User user : tagSubscriptions) {

            if(user.getId().equals(question.getUser().getId()) || user.getId().equals(answer.getUser().getId()))
                continue;

            Notification notification = new Notification();

            String description = MSG_TAGGED;

            notification.setDescription(description);
            notification.setType(NotificationType.ANSWER_FOR_TAGGED_QUESTION);
            notification.setUser(user);
            notification.setUiNotified(false);
            notification.setEmailDelivered(false);
            notification.setQuestion(question);

            templateParams.put("description", description);
            templateParams.put("userName", user.getFirstName());

            notificationRepository.save(notification);

            try {
                mailSenderService.sendEmail(NotificationType.ANSWER_FOR_TAGGED_QUESTION, subject, templateParams, user.getEmail());
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
