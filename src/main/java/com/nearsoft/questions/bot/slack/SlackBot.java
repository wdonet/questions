package com.nearsoft.questions.bot.slack;

import com.nearsoft.questions.bot.AbstractBot;
import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.service.QuestionService;
import com.ullink.slack.simpleslackapi.SlackAttachment;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;

import java.io.IOException;
import java.util.Collection;

public class SlackBot extends AbstractBot<SlackEvent> implements SlackMessagePostedListener {

    private final String appSite;

    public SlackBot(String authToken, String appSite, QuestionService questionService) throws IOException {
        super(questionService);
        final SlackSession slack = SlackSessionFactory.createWebSocketSlackSession(authToken);
        slack.connect();
        slack.addMessagePostedListener(this);
        this.appSite = appSite;
    }

    @Override
    public void onEvent(SlackMessagePosted event, SlackSession session) {
        if (event.getSender().isBot()) {
            return; //ignore bots
        }
        process(new SlackEvent(event, session), event.getMessageContent());
    }

    @Override
    protected void sendMessage(SlackEvent event, Answer answers) {
        event.getSlackSession().sendMessage(event.getMessagePosted().getChannel(), answers.getDescription());
    }

    @Override
    protected void sendMessage(SlackEvent event, Collection<Question> questions) {
        SlackSession slackSession = event.getSlackSession();
        SlackChannel channel = event.getMessagePosted().getChannel();
        slackSession.sendMessage(channel, "Encontramos varias respuestas, wacha:"); //TODO
        for (Question q : questions) {
            slackSession.sendMessage(channel, null, new SlackAttachment(q.getTitle(), null, appSite + "/question/" + q.getId(), null));
        }
    }
}