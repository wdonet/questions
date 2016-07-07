package com.nearsoft.questions.service;


import com.nearsoft.questions.domain.Question;
import org.apache.commons.lang3.StringUtils;
import org.dsaw.botin.Bot;
import org.dsaw.botin.BotManager;
import org.dsaw.botin.bot.skype.SkypeBot;
import org.dsaw.botin.bot.slack.SlackBot;
import org.dsaw.botin.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BotService {

    private final BotManager botManager;
    private final static Pattern QUESTION_MARK = Pattern.compile("(\\s*)\\?(\\s*)$", Pattern.CASE_INSENSITIVE);

    @Autowired
    public BotService(QuestionService questionService, Environment environment) {
        botManager = new BotManager();
        String slackApiToken = environment.getProperty("SLACK_API_TOKEN");
        if (StringUtils.isNoneBlank(slackApiToken)) {
            try {
                botManager.add(new SlackBot(slackApiToken));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String skypeClientId = environment.getProperty("SKYPE_CLIENT_ID");
        String skypeClientSecret = environment.getProperty("SKYPE_CLIENT_SECRET");
        if (StringUtils.isNoneBlank(skypeClientId) && StringUtils.isNoneBlank(skypeClientSecret)) {
            botManager.add(new SkypeBot(skypeClientId, skypeClientSecret, "skype"));
        }

        final String appSite = environment.getProperty("application.site");

        botManager.register(SkypeBot.class, (b, m) -> answer(questionService, appSite, b, m.getFrom(), m.getContent())); //echo 1 - 1 chats
        botManager.register(SlackBot.class, (b, m) -> answer(questionService, appSite, b, m.getTo(), m.getContent())); //echo on groups
    }

    private void answer(QuestionService questionService, String appSite, Bot b, String to, String content) {
        Matcher matcher = QUESTION_MARK.matcher(content);
        if (matcher.find()) {
            List<Question> questions = questionService.search(matcher.replaceAll("")); //TODO most likely? rank?
            if (questions.isEmpty()) {
                return;
            }
            if (questions.size() == 1) {
                b.send(new Message(to, "The following question is similar to yours, take a look!"));
            } else {
                b.send(new Message(to, "The following questions are similar to yours, take a look!"));
            }
            for (Question q : questions) {
                b.send(new Message(to, appSite + "/question/" + q.getId()));
            }
        }
    }

    @Bean
    public HandlerMapping handlerMethod() {
        return botManager.getHandlerMapping();
    }
}
