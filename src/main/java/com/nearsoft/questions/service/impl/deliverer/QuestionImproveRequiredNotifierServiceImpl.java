package com.nearsoft.questions.service.impl.deliverer;

import com.nearsoft.questions.domain.Notification;
import com.nearsoft.questions.domain.NotificationType;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.repository.NotificationRepository;
import com.nearsoft.questions.repository.QuestionRepository;
import com.nearsoft.questions.service.MailSenderService;
import com.nearsoft.questions.service.NotificationDelivererService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;


@Service
public class QuestionImproveRequiredNotifierServiceImpl implements NotificationDelivererService {

    public static final String QUESTION_ID_PARAM = "com.nsquestions.question.id";
    public static final String MESSAGE_TO_IMPROVE_PARAM = "com.nsquestions.question.message_to_improve";

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    ParameterReader parameterReader;

    @Override
    public void sendNotification(Map<String, String> parametersMap) {

        String messageToImprove = parameterReader.getString(parametersMap, MESSAGE_TO_IMPROVE_PARAM);
        Question question = questionRepository.findOne(parameterReader.getLong(parametersMap, QUESTION_ID_PARAM));
        User user = question.getUser();

        Notification notification = new Notification();

        notification.setDescription(messageToImprove);
        notification.setType(NotificationType.IMPROVEMENT);
        notification.setUser(user);

        notificationRepository.save(notification);


    }
}
