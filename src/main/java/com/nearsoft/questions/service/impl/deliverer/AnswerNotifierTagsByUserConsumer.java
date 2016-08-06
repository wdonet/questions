package com.nearsoft.questions.service.impl.deliverer;

import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.TagSubscription;
import com.nearsoft.questions.domain.auth.User;

import java.util.function.Consumer;

public class AnswerNotifierTagsByUserConsumer implements Consumer<TagSubscription> {

    private User currentUser;
    private StringBuilder tagsList = new StringBuilder();
    private Question question;
    private Answer answer;
    private NewAnswerNotifierServiceImpl newAnswerNotifierService;

    public AnswerNotifierTagsByUserConsumer(Question question, Answer answer, NewAnswerNotifierServiceImpl newAnswerNotifierService) {
        this.question = question;
        this.answer = answer;
        this.newAnswerNotifierService = newAnswerNotifierService;
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
            newAnswerNotifierService.sendNotification(question, answer, tagsList.toString(), currentUser);
            tagsList = new StringBuilder();
            currentUser = user;
            appendTagName(user, tagsList, tagLabel);
        }
    }

    public void sendRemainingNotifications(){
        newAnswerNotifierService.sendNotification(question, answer, tagsList.toString(), currentUser);
    }

    private void appendTagName(User user, StringBuilder tagsList, String name) {

        if (user.getId().equals(answer.getUser().getId())) {
            //Omit notification for the user who creates the answer
            return;
        }

        if (tagsList.length() > 0) {
            tagsList.append(",");
        } else {
            tagsList.append(name);
        }
    }
}