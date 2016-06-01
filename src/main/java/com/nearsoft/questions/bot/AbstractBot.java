package com.nearsoft.questions.bot;

import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.service.QuestionService;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractBot<E> {

    private final static Pattern pattern = Pattern.compile("(\\s*)\\?(\\s*)$", Pattern.CASE_INSENSITIVE);

    private final QuestionService questionService;

    public AbstractBot(QuestionService questionService) {
        this.questionService = questionService;
    }

    protected abstract void sendMessage(E event, Answer answers);

    protected abstract void sendMessage(E event, Collection<Question> questions);

    protected void process(E event, String message) {
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            List<Question> questions = questionService.search(matcher.replaceAll("")); //TODO most likely? rank?
            if (questions.isEmpty()) {
                // log
                return;
            }
            if (questions.size() == 1) {
                Answer answer = new Answer();
            answer.setDescription("Con ana Luisa 62 555 1234");
                if (answer == null) {
                    // log
                } else {
                    sendMessage(event, answer);
                }
            } else {
                sendMessage(event, questions);
            }
        } else {
            // log??
        }
    }

}