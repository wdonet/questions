<!DOCTYPE html>
<#assign username="nsq@nearsoft.com">

<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="/css/styles.css">
        <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
    </head>
    <body>
    <#include "header.ftl">
        <div>${question.title}</div>
        <div>${question.description}</div>
        <div>
        <#list question.tags as tag>
            &nbsp;
            <span>${tag.name}</span>
        <#else>
            <span>No tags</span>
        </#list>
        </div>
    <#list question.answers as answer>
        <div>${answer.description}</div>
    <#else>
        <div>No answers</div>
    </#list>
        <form method="post" action="/answer">
            <h3>Add an answer</h3>
            <textarea name="description" type="textarea" placeholder="Add a detailed answer"></textarea>
            <input name="questionId" type="hidden" value="${question.id}">
            <input type="hidden" name="username" value="${(user.email)!username}"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" value="Add">
        </form>
    </body>

</html>
