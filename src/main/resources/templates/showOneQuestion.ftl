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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.2/css/tether.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/css/drop-theme-basic.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/css/drop-theme-arrows.css"/>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">

    <script src="https://use.fontawesome.com/4eda52b947.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="//cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
    <script src="https://cdn.jsdelivr.net/highlight.js/latest/highlight.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.2/js/tether.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/js/drop.min.js"></script>
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
        <div id="edit-title-input-div"> <input name="title" form="editQuestionForm" type="text" id="questionTitleInput"></div>
    <div id="questionInfo">
    <div class="tag-icon"><i class="fa fa-tags"></i>Categories:</div>
    <#if question.tags?size gt 0 >
        <div id="tag-div" class="tag-div">
            <#list question.tags as tag>
                <span class="tags">${tag.name}<#if tag?has_next >,</#if></span>
            </#list>
        </div>
    <#else>
        <span>No tags</span>
    </#if>
        <div class="owner" id="owner"><i class="fa fa-user"></i>Asked By
            <img src="${question.user.photoUri!"#"}">
            <span>${(question.authorName)!""}</span>
        </div>
        <div class="date">
            <i class="fa fa-clock-o"></i>
            <label class="date-text">${(question.createdAt)!""}</label>
        </div>
        <#if isQuestionOwner && isNotClosed>
            <div class="edit-question-div"><button class="edit-btn" id="edit-question-btn" type="submit">Edit</button></div>
        </#if>
        </div>
        <input name="id" type="hidden" form="editQuestionForm" value="${question.id?c}">
        <#if question.description?? && question.description?length &gt; 0>
            <div class="question-description">${question.description}</div>
            <div id="edit-question-form-div">
             <form method="post" id="editQuestionForm" action="/question/update">
                 <textarea name="description" class="edit-question-input" type="textarea" id="question-description-textarea"></textarea>
                 <h2 class="tags-title-form">Tags:</h2>
                 <input name="tags" type="text" id="tag-edit-input" value="">
                 <input class="add-button" type="button" id="cancelEditBtn" value="Cancel">
                 <input class="add-button" type="submit" value="Edit">
             </form>
            </div>
        <#else>
            <div></div>
        </#if>
        <div class="all-comments-question">
            <#list question.comments as comment>
                <div class="comments-question-cont">
                    <div class="owner"><span>${(comment.user.fullName)!""}</span></div>
                    <#-- Format Month DD at HH:mm -->
                    <div class="date"><i class="fa fa-clock-o"></i><label class="date-text">${(comment.createdAt)!""} </label></div>
                    <div class="comment-box-question">${(comment.description)!""}</div>
                </div>
            </#list>
        </div>
        <div class="add-comment-cont">
            <a class="open-add-comment">Add Comment</a>
            <a class="show-hide-comments">Show/Hide Comments</a>
        </div>
        <div>
            <div class="question-comment-cont" style="display: none;">
                <form id="question-comment-form" method="post" action="/comments/question/">
                    <input style="visibility: hidden;" name="sourceId" type="number" value="${question.id?c}">
                    <textarea name="description" type="textarea" class="comment-textarea" placeholder="Add your comment here" rows="5" required></textarea>
                    <button type="submit" class="add-comment-btn">Add Comment</button>
                </form>
            </div>
        </div>
            <div class="answer-date">${(question.createdAt[0..9] + ", " + question.createdAt[11..15] + " hrs.")!""}</div>
        </div>
        <h2 class="answers-title">ANSWERS</h2>
    <#list question.answers as answer>
        <div class="answers-cont">
            <div class="author-cont">
                <div class="owner">Answered By
                    <img src="${answer.user.photoUri!"/img/user-research-uxteam.jpg"}">
                    <span>${answer.authorName!""}</span>
                </div>
                <div class="date">
                    <i class="fa fa-clock-o"></i>
                    <label class="date-text">${(answer.createdAt)!""}</label>
                </div>
                    <div class="validation-cont">
                        <form action="/answer/voteUp" method="post" class="validation-positive">
                            <input name="answerId" type="hidden" value="${answer.id?c}">
                            <input name="questionId" type="hidden" value="${question.id?c}">
                            <button class="val-pos fa fa-arrow-up" type="submit"></button>
                            <div class="votes">${answer.votesUp}</div>
                        </form>
                        <form action="/answer/voteDown" method="post" class="validation-negative">
                            <input name="answerId" type="hidden" value="${answer.id?c}">
                            <input name="questionId" type="hidden" value="${question.id?c}">
                            <button class="val-neg fa fa-arrow-down" type="submit"></button>
                            <div class="votes">${answer.votesDown}</div>
                        </form>
                     <#if isQuestionOwner && answer.status != 'ACCEPTED' >
                        <FORM action="/answer/accepted" method="post" class="validation-negative">
                            <input name="answerId" type="hidden" value="${answer.id?c}">
                            <input name="questionId" type="hidden" value="${question.id?c}">
                            <button type="submit" class="add-button">Accept</button>
                        </FORM>
                     </#if>
                    <#if userId == answer.user.id>
                        <button class="edit-btn answer" id="edit-answer-btn-${answer?counter}" value="${answer?counter}">Edit</button>
                    </#if>
                    </div>
            </div>
            <div class="answers" id="answer-description-div-${answer?counter}">${answer.description}</div>
            <div id="edit-answer-form-div-${answer?counter}" class="edit-answer-form-div">
                <form method="post" id="editAnswerForm-${answer?counter}" action="/answer/update">
                    <input name="answerId" type="hidden" value="${answer.id?c}">
                    <input name="questionId" type="hidden" value="${question.id?c}">
                    <textarea name="description" class="edit-answer-input" type="textarea" id="answer-description-textarea-${answer?counter}"></textarea>
                    <button class="add-button cancel-edit-answer-btn" type="button" value="${answer?counter}">Cancel</button>
                    <input class="add-button" type="submit" value="Edit">
                </form>
            </div>
        <div class="all-comments-answer">
            <#list answer.comments as comment>
                <div class="comments-question-cont">
                    <div class="owner"><span>${(comment.user.fullName)!""}</span></div>
                    <#-- Format Month DD at HH:mm -->
                    <div class="date"><i class="fa fa-clock-o"></i><label class="date-text">${(comment.createdAt)!""} </label></div>
                    <div class="comment-box-question">${(comment.description)!""}</div>
                </div>
            </#list>
        </div>

            <div class="add-comment-cont">
                <a href="#">Add Comment</a>
                <a href="#">Hide Comments</a>
            </div>
            <div>
                <form id="answer-comment-form" method="post" action="/comments/answer/">
                    <input style="visibility: hidden;" name="sourceId" type="number" value="${answer.id?c}">
                    <textarea name="description" type="textarea" class="comment-textarea" placeholder="Add your comment here" rows="5" required></textarea>
                    <button type="submit" class="add-comment-btn">Add Comment</button>
                </form>
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
            <input name="questionId" type="hidden" value="${question.id?c}">
        <#--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->
            <input class="add-button" type="submit" value="Add">
        </form>
    </#if>
    </div>
</div>
<script src="/js/header.js"></script>
</body>

</html>
