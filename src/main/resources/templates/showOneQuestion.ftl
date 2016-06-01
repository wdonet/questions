<!DOCTYPE html>
<#assign username="nsq@nearsoft.com">

<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
    <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'>
    <script src="https://use.fontawesome.com/4eda52b947.js"></script>
    <link rel="stylesheet" href="//cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/highlight.js/latest/styles/github.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="/js/linkify.js"></script>
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
        <div class="coments-question-cont">
            <div class="owner"><span>${(question.user.fullName)!""}</span></div>
            <div class="date"><i class="fa fa-clock-o"></i> May 13 at 7:26</div>
            <div class="comment-box-question">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
            tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,.</div>
        </div>
        <div class="add-comment-cont">
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
                        <div class="validation-positive">
                            <a href="#" class="val-pos"><i class="fa fa-arrow-up"></i></a>
                            <div class="votes">${answer.votesUp}</div>
                        </div>
                        <div class="validation-negative">
                            <a href="#" class="val-neg"><i class="fa fa-arrow-down"></i></a>
                            <div class="votes">${answer.votesDown}</div>
                        </div>
                    </div>
            </div>
            <div class="answers">${answer.description}</div>
            <div class="coments-question-cont">
                <div class="owner"><span>${(question.user.fullName)!""}</span></div>
                <div class="date"><i class="fa fa-clock-o"></i> May 13 at 7:26</div>
                <div class="comment-box-question">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,.</div>
            </div>
            <div class="add-comment-cont">
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
