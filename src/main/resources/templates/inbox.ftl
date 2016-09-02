<!DOCTYPE html>

<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="/css/general.css">
    <link rel="stylesheet" type="text/css" href="/css/searchForm.css">
    <link rel="stylesheet" type="text/css" href="/css/showQuestion.css">

    <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.2/css/tether.min.css"/>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/css/drop-theme-basic.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/css/drop-theme-arrows.css"/>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">

    <script src="https://use.fontawesome.com/4eda52b947.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.2/js/tether.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/js/drop.min.js"></script>
    <script src="/js/search.js"></script>
</head>
<body class="showquestions">
<#include "header.ftl">
<div class="search-container">
    <input class="input-search-question" name="query" type="text" placeholder="What are you looking for?">
    <button type="button" value="" class="search-submit positioning"></button>
</div>
<div class="suggestion-wrapper">
    <a href="#" class="back-btn">« BACK </a>

    <div class="question-cont">
    <#if notifications?has_content>
        <h1 class="unanswered-q">Notifications</h1>
        <div class="action-for-notifications-bar">
            <input type="checkbox" id="select_all" />
            <i class="fa fa-trash i-button" id="deleteNotifications"></i>
        </div>
        <ul>
            <#list notifications as notification>
                <#switch notification.type>
                    <#case "NEW_QUESTION">
                        <#assign icon = "fa fa-tags added-tag">
                        <#assign details = "Question added">
                        <#assign detailsClass = "added-tag">
                        <#break>
                    <#case "IMPROVEMENT">
                        <#assign icon = "fa fa-arrow-circle-up improvement">
                        <#assign details = "Question improved">
                        <#assign detailsClass = "improvement">
                        <#break>
                    <#case "ANSWER_ACCEPTED">
                        <#assign icon = "fa fa-ban improvement">
                        <#assign details = "Answer accepted">
                        <#assign detailsClass = "improvement">
                        <#break>
                    <#case "QUESTION_VOTED_UP">
                        <#assign icon = "fa fa-thumbs-o-up improvement">
                        <#assign details = "Question voted up">
                        <#assign detailsClass = "improvement">
                        <#break>
                    <#case "QUESTION_VOTED_DOWN">
                        <#assign icon = "fa fa-thumbs-o-down close-improvement">
                        <#assign details = "Question voted down">
                        <#assign detailsClass = "close-improvement">
                        <#break>
                    <#case "ANSWER_VOTED_UP">
                        <#assign icon = "fa fa-thumbs-up improvement">
                        <#assign details = "Answer voted up">
                        <#assign detailsClass = "improvement">
                        <#break>
                    <#case "ANSWER_VOTED_DOWN">
                        <#assign icon = "fa fa-thumbs-down close-improvement">
                        <#assign details = "Answer voted down">
                        <#assign detailsClass = "close-improvement">
                        <#break>
                    <#case "ANSWER_FOR_TAGGED_QUESTION">
                        <#assign icon = "fa fa-lightbulb-o added-tag">
                        <#assign details = "Answer for tagged question">
                        <#assign detailsClass = "added-tag">
                        <#break>
                    <#default>
                        <#assign icon = "fa fa-arrow-circle-up improvement">
                        <#assign details = "Question improved">
                        <#assign detailsClass = "improvement">
                </#switch>
                <li class="notifications-li">
                    <input type="checkbox" id="notification_${notification.id}" class="notification_checkbox" />
                    <div class="content-notification">
                        <div>
                            <a class="notification-remove-anchor-style" href="/question/${notification.question.id}">
                            <i class="${icon}"></i>${notification.description}
                            <span class="${detailsClass}">${details}</span>
                            </a>
                        </div>
                        <div>
                            <label class="date-text">${notification.createdAt?string["dd/MM/yyyy HH:mm"]}</label>
                        </div>
                    </div>
                </li>
            </#list>
        </ul>
    </#if>
    <#if !notifications?has_content>
        Nothing new for you
    </#if>
    </div>
</div>
<script src="/js/header.js"></script>
</body>
</html>
