package com.nearsoft.questions.bot.slack;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;

class SlackEvent {

    private final SlackMessagePosted messagePosted;
    private final SlackSession slackSession;

    SlackEvent(SlackMessagePosted messagePosted, SlackSession slackSession) {
        this.messagePosted = messagePosted;
        this.slackSession = slackSession;
    }

    SlackMessagePosted getMessagePosted() {
        return messagePosted;
    }

    SlackSession getSlackSession() {
        return slackSession;
    }
}
