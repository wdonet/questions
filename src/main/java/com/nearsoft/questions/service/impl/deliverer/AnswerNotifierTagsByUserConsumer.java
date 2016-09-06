package com.nearsoft.questions.service.impl.deliverer;

import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.Notification;
import com.nearsoft.questions.domain.NotificationType;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.TagSubscription;
import com.nearsoft.questions.domain.auth.User;

import org.json.simple.JSONObject;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

class AnswerNotifierTagsByUserConsumer implements Consumer<TagSubscription> {

    private NewAnswerNotifierServiceImpl newAnswerNotifierService;
    private Question question;
    private Answer answer;
    private Notification notification;
    private String currentTag;
    private Set<Long> usersNotified;

    AnswerNotifierTagsByUserConsumer(Question question, Answer answer, NewAnswerNotifierServiceImpl newAnswerNotifierService) {
        this.question = question;
        this.answer = answer;
        this.newAnswerNotifierService = newAnswerNotifierService;
        usersNotified = new HashSet<>();
        usersNotified.add(answer.getUser().getId());
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
            newAnswerNotifierService.sendNotification(question, answer, currentTag, user, notification);
        }
    }

    @SuppressWarnings("unchecked")
    private void prepareNotification() {
        notification = new Notification();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("text", answer.getDescription());
        jsonObject.put("questionId", question.getId());
        jsonObject.put("answerId", answer.getId());
        jsonObject.put("tag", currentTag);

        notification.setDescription(jsonObject.toJSONString());
        notification.setType(NotificationType.ANSWER_FOR_TAGGED_QUESTION);

    }
}