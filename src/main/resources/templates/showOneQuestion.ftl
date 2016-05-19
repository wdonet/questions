<!DOCTYPE html>
<#assign username="nsq@nearsoft.com">

<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
    <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
</head>
<body>
<#include "header.ftl">
<div class="suggestion-wrapper">
    <div class="question-cont">
        <div class="question-title">${question.title}</div>
    <#list question.tags as tag>
        <span class="tags">${tag.name}</span>
    <#else>
        <span>No tags</span>
    </#list>
        <div class="question-description">${question.description}</div>
        <div class="author-name">asked by <span>${(question.authorName)!""}</span>
            <img src="${question.user.profile.photoUri!"#"}">
            <div class="answer-date">${(question.createdAt[0..9] + ", " + question.createdAt[11..15] + " hrs.")!""}</div>
        </div>
        <h2 class="answers-title">What people have answered</h2>
    <#list question.answers as answer>
        <div class="answers-cont">

            <div class="answers">${answer.description}</div>

            <div class="author-cont">
                <div class="author-name">answered by <span>${answer.authorName!""}</span>
                    <img src="${answer.user.profile.photoUri!"#"}"></div>
                    <div class="answer-date">${(answer.createdAt[0..9] + ", " + answer.createdAt[11..15] + " hrs.")!""}</div>
            </div>
        </div>
    <#else>
        <div>No answers</div>
    </#list>
    <#if !onlyOneAnswer || question.answers?size == 0>
        <form method="post" action="/answer">
            <h3 class="answers-title">Add an answer</h3>
            <textarea class="add-answer-input" name="description" type="textarea"
                    placeholder="Add a detailed answer"></textarea>
            <input name="questionId" type="hidden" value="${question.id}">
        <#--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->
            <input class="add-button" type="submit" value="Add">
        </form>
    </#if>
    </div>
</div>
</body>

</html>
