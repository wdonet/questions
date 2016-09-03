package com.nearsoft.questions.service.impl.deliverer;

import com.nearsoft.questions.domain.Notification;
import com.nearsoft.questions.domain.NotificationType;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.TagSubscription;
import com.nearsoft.questions.domain.auth.User;

import org.json.simple.JSONObject;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

class QuestionNotifierTagsByUserConsumer implements Consumer<TagSubscription> {

    private Question question;
    private NewQuestionNotifierServiceImpl newQuestionNotifierService;
    private String currentTag;
    private Set<Long> usersNotified;
    private Notification notification;

    QuestionNotifierTagsByUserConsumer(Question question, NewQuestionNotifierServiceImpl newQuestionNotifierService) {
        this.question = question;
        this.newQuestionNotifierService = newQuestionNotifierService;
        usersNotified = new HashSet<>();
        usersNotified.add(question.getUser().getId());
    }

    @Override
    public void accept(TagSubscription tagSubscription) {
        User user = tagSubscription.getUser();
        String tagLabel = tagSubscription.getTag().getName();

        if (!tagLabel.equals(currentTag)) {
            currentTag = tagLabel;
            prepareNotification();
        }

        if (!usersNotified.contains(user.getId())) {
            usersNotified.add(user.getId());
            newQuestionNotifierService.sendNotification(question, notification, user, currentTag);
        }
    }

    @SuppressWarnings("unchecked")
    private void prepareNotification() {
        notification = new Notification();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("text", question.getTitle());
        jsonObject.put("questionId", question.getId());
        jsonObject.put("tag", currentTag);

        notification.setDescription(jsonObject.toJSONString());
        notification.setType(NotificationType.NEW_QUESTION);

    }
}