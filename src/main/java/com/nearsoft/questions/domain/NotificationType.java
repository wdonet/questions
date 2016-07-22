package com.nearsoft.questions.domain;


public enum NotificationType {


    IMPROVEMENT("mail/improvement.ftl"),
    ADD("mail/newQuestion.ftl"),
    CLOSE("mail/close.ftl"),
    QUESTION_VOTED_UP("mail/questionVotedUp.ftl"),
    QUESTION_VOTED_DOWN("mail/questionVotedDown.ftl"),
    ANSWER_VOTED_UP("mail/answerVotedUp.ftl"),
    ANSWER_VOTED_DOWN("mail/answerVotedDown.ftl");

    private String mailTemplateName;


    NotificationType(String mailTemplateName) {
        this.mailTemplateName = mailTemplateName;
    }

    public String getMailTemplateName() {
        return mailTemplateName;
    }


}
