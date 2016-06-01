package com.nearsoft.questions.service.impl.deliverer;

import com.nearsoft.questions.service.MailSenderService;
import com.nearsoft.questions.service.NotificationDelivererService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QuestionImprovedNotifierServiceImpl implements NotificationDelivererService {

    @Override
    public void sendNotification(Map<String, String> parametersMap) {
        //notificationMailSender.execute();
    }
}
