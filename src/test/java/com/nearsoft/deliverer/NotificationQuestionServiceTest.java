package com.nearsoft.deliverer;

import com.nearsoft.questions.config.QuestionsApplication;
import com.nearsoft.questions.service.NotificationDelivererService;
import com.nearsoft.questions.service.NotificationService;
import com.nearsoft.questions.service.impl.deliverer.NotificationQuestionDelivererServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rjimenez on 5/31/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(QuestionsApplication.class)
public class NotificationQuestionServiceTest {
    @Autowired
    NotificationService notificationService;


    @Test
    public void contextLoads() {
        Map<String, String> XD = new HashMap<>();
        NotificationDelivererService n = notificationService.sendNotification(NotificationQuestionDelivererServiceImpl.class);
        n.sendNotification(XD);
    }

}
