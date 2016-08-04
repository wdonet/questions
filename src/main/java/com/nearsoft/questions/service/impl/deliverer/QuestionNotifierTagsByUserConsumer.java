package com.nearsoft.questions.service.impl.deliverer;

import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.TagSubscription;
import com.nearsoft.questions.domain.auth.User;

import java.util.function.Consumer;

public class QuestionNotifierTagsByUserConsumer implements Consumer<TagSubscription> {


    private User currentUser;
    private StringBuilder tagsList = new StringBuilder();
    private Question question;
    private NewQuestionNotifierServiceImpl newQuestionNotifierService;

    public QuestionNotifierTagsByUserConsumer(Question question, NewQuestionNotifierServiceImpl newQuestionNotifierService) {
        this.question = question;
        this.newQuestionNotifierService = newQuestionNotifierService;
    }

    @Override
    public void accept(TagSubscription tagSubscription) {
        User user = tagSubscription.getUser();
        String tagLabel = tagSubscription.getTag().getName();

        if (currentUser == null) {
            currentUser = user;
        }

        if (user.equals(currentUser)) {
            appendTagName(user, tagsList, tagLabel);
        } else {
            newQuestionNotifierService.sendNotification(question, tagsList.toString(), currentUser);
            tagsList = new StringBuilder();
            currentUser = user;
            appendTagName(user, tagsList, tagLabel);
        }
    }

    public void sendRemainingNotifications() {
        newQuestionNotifierService.sendNotification(question, tagsList.toString(), currentUser);
    }

    private void appendTagName(User user, StringBuilder tagsList, String name) {

        if (user.getId().equals(question.getUser().getId())) {
            //Omit notification for the user who creates the question
            return;
        }

        if (tagsList.length() > 0) {
            tagsList.append(",");
        } else {
            tagsList.append(name);
        }
    }
}