package com.nearsoft.questions.service;

import java.util.Map;

public interface NotificationDelivererService {
    void sendNotification(Map<String, String> parametersMap);

}
