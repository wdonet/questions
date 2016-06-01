package com.nearsoft.questions.domain;


import org.springframework.beans.factory.annotation.Value;

public enum NotificationType {


    IMPROVEMENT("mail/improvement.ftl"), ADD("mail/newQuestion.ftl"), CLOSE("mail/close.ftl");

    private String mailTemplateName;


    private NotificationType(String mailTemplateName) {
        this.mailTemplateName = mailTemplateName;
    }

    public String getMailTemplateName() {
        return mailTemplateName;
    }


}
