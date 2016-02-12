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
        <div class="owner">asked by &nbsp; <span>${(question.user.fullName)!""}</span></div>
        <div class="question-description">${question.description}</div>
    <#list question.answers as answer>
        <div class="answers-cont">
            <h2 class="answers-title">What people have answered</h2>

            <div class="author-cont">
                <div class="author-name">answered by &nbsp; <!--img src="${answer.user.imageUrl!"#"}"--> <span>${answer.user.fullName!""}</span></div>
                <div class="answer-date">- 1 Hour Ago</div>
            </div>
            <div class="answers">${answer.description}</div>
        </div>
    <#else>
        <div>No answers</div>
    </#list>
        <form method="post" action="/answer">
            <h3 class="answers-title">Add an answer</h3>
            <textarea class="add-answer-input" name="description" type="textarea"
                      placeholder="Add a detailed answer"></textarea>
            <input name="questionId" type="hidden" value="${question.id}">
        <#--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->
            <input class="add-button" type="submit" value="Add">
        </form>
    </div>
</div>
</body>

</html>
