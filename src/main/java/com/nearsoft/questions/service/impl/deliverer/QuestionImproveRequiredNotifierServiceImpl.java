package com.nearsoft.questions.service.impl.deliverer;

import com.nearsoft.questions.service.MailSenderService;
import com.nearsoft.questions.service.NotificationDelivererService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by rjimenez on 6/1/16.
 */
@Service
public class QuestionImproveRequiredNotifierServiceImpl implements NotificationDelivererService {

    @Autowired
    MailSenderService mailSenderService;

    @Override
    public void sendNotification(Map<String, String> parametersMap) {

    }
}
