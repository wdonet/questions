package com.nearsoft.questions.service.impl.deliverer;

import com.nearsoft.questions.domain.*;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.repository.NotificationRepository;
import com.nearsoft.questions.repository.QuestionRepository;
import com.nearsoft.questions.repository.TagsSubscriptionRepository;
import com.nearsoft.questions.service.MailSenderService;
import com.nearsoft.questions.service.NotificationDelivererService;
import com.nearsoft.questions.service.TagsSubscriptionService;
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


        for(User user : tagSubscriptions) {
            Notification notification = new Notification();

            notification.setDescription(NEW_QUESTION_MSG + question.getDescription());
            notification.setType(NotificationType.ADD);
            notification.setUser(user);

            templateParams.put("userName", user.getFirstName());

            notificationRepository.save(notification);

            //mailSenderService.sendEmail(toMails, subject, templateParams,NotificationType.ADD);
        }




    }
}
