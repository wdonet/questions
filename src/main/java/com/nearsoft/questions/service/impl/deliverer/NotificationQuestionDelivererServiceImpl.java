package com.nearsoft.questions.service.impl.deliverer;

import com.nearsoft.questions.service.NotificationDelivererService;
import org.springframework.stereotype.Service;

/**
 * Created by rjimenez on 5/31/16.
 */
@Service
public class NotificationQuestionDelivererServiceImpl implements NotificationDelivererService {


    @Override
    public void sendNotification() {
        System.out.println("shiit");
    }
}
