<!DOCTYPE html>

<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
    <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
</head>
<body>
<#include "header.ftl">
<div class="suggestion-wrapper">
    <h1 class="unanswered-questions-title"><img src="/img/question-icon.png" class="question-icon"> Questions</h1>
    <ul class="suggestions-cont">
    <#list questionList as question>
        <li>
            <a class="respuesta-titulo" href="/question/${question.id}">${question.title}</a>

            <div class="respuestas-total">- ${question.totalAnswers} Answers</div>
            <div class="tags-cont">
                <div class="tag-icon"><img src="/img/tag-icon.png" alt="">Tags:</div>
                <#list question.tags as tag>
                    <span class="tags"><a href="/question/tag/${tag.id}">${tag.name}</a></span>
                <#else>
                    <span class="tags">No tags</span>
                </#list>
                <div class="owner">asked by &nbsp; <span>${(question.user.fullName)!""}</span></div>
            </div>
        </li>
    <#else>
        <div>No</div>
    </#list>
    </ul>
</div>


</body>

</html>
