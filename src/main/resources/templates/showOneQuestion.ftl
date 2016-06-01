<!DOCTYPE html>
<#assign username="nsq@nearsoft.com">

<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="/css/general.css">
    <link rel="stylesheet" type="text/css" href="/css/showQuestion.css">
    <link rel="stylesheet" type="text/css" href="/css/searchForm.css">
    <link rel="stylesheet" type="text/css" href="/css/tags.css">
    <link rel="stylesheet" type="text/css" href="/css/forms.css">
    <link rel="stylesheet" type="text/css" href="/css/validation.css">
    <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="//cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/highlight.js/latest/styles/github.min.css">

    <script src="https://use.fontawesome.com/4eda52b947.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="//cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
    <script src="https://cdn.jsdelivr.net/highlight.js/latest/highlight.min.js"></script>
    <script src="/js/showOneQuestion.js"></script>
</head>
<body class="question">
<#include "header.ftl">
    <div class="search-container">
        <input class="input-search-question" name="query" type="text" placeholder="What are you looking for?">
        <button type="button" value="" class="search-submit positioning"></button>
    </div>
<div class="suggestion-wrapper">
    <a href="#" class="back-btn">« BACK </a>
    <div class="question-cont">
        <div class="question-title">${question.title}</div>
    <#list question.tags as tag>
        <div class="tag-icon"><i class="fa fa-tags"></i>Categories:</div>
        <span class="tags">${tag.name}</span>
            <div class="owner"><i class="fa fa-user"></i>Asked By ${(question.authorName)!""}</div>
            <img src="${question.user.photoUri!"#"}">
    <#else>
        <span>No tags</span>
    </#list>
        <#if question.description?? && question.description?length &gt; 0>
            <div class="question-description">${question.description}</div>
        <#else>
            <div></div>
        </#if>
        <#list question.comments as comment>
            <div class="comments-question-cont">
                <div class="owner"><span>${(comment.user.fullName)!""}</span></div>
                <#-- Format Month DD at HH:mm -->
                <div class="date"><i class="fa fa-clock-o"></i>${(comment.createdAt)!""}</div>
                <div class="comment-box-question">${(comment.description)!""}</div>
            </div>
        </#list>

        <div class="add-comment-cont">
            <div>
                <form id="question-comment-form" method="post" action="/comments/question/">
                    <input style="visibility: hidden;" name="sourceId" type="number" value="${question.id}">
                    <textarea name="description" type="textarea" class="comment-textarea" placeholder="Add your comment here"></textarea>
                </form>
            </div>
            <a href="#">Add Comment</a>
            <a href="#">Hide Comments</a>
        </div>
            <div class="answer-date">${(question.createdAt[0..9] + ", " + question.createdAt[11..15] + " hrs.")!""}</div>
        </div>
        <h2 class="answers-title">ANSWERS</h2>
    <#list question.answers as answer>
        <div class="answers-cont">
            <div class="author-cont">
                <div class="owner">Answered By
                    <img src="${answer.user.photoUri!"#"}">
                    <span>${answer.authorName!""}</span>
                </div>
                    <div class="answer-date">${(answer.createdAt[0..9] + ", " + answer.createdAt[11..15] + " hrs.")!""}</div>
                    <div class="validation-cont">
                        <form action="/answer/voteUp" method="post" class="validation-positive">
                            <input name="answerId" type="hidden" value="${answer.id}">
                            <input name="questionId" type="hidden" value="${question.id}">
                            <button class="val-pos fa fa-arrow-up" type="submit"></button>
                            <div class="votes">${answer.votesUp}</div>
                        </form>
                        <form action="/answer/voteDown" method="post" class="validation-negative">
                            <input name="answerId" type="hidden" value="${answer.id}">
                            <input name="questionId" type="hidden" value="${question.id}">
                            <button class="val-neg fa fa-arrow-down" type="submit"></button>
                            <div class="votes">${answer.votesDown}</div>
                        </form>
                    </div>
            </div>
            <div class="answers">${answer.description}</div>
            <#list answer.comments as comment>
                <div class="comments-question-cont">
                    <div class="owner"><span>${(comment.user.fullName)!""}</span></div>
                    <#-- Format Month DD at HH:mm -->
                    <div class="date"><i class="fa fa-clock-o"></i>${(comment.createdAt)!""}</div>
                    <div class="comment-box-question">${(comment.description)!""}</div>
                </div>
            </#list>

            <div class="add-comment-cont">
                <div>
                    <form id="answer-comment-form" method="post" action="/comments/answer/">
                        <input style="visibility: hidden;" name="sourceId" type="number" value="${answer.id}">
                        <textarea name="description" type="textarea" class="comment-textarea" placeholder="Add your comment here"></textarea>
                    </form>
                </div>
                <a href="#">Add Comment</a>
                <a href="#">Hide Comments</a>
            </div>
        </div>
    <#else>
        <div>No answers</div>
    </#list>
    <#if !onlyOneAnswer || question.answers?size == 0>
        <form method="post" action="/answer">
            <h3 class="leave-answers">Add an answer</h3>
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
