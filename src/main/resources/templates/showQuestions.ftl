<#import "/spring.ftl" as spring/>
<!DOCTYPE html>

<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
    <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'>
    <script src="https://use.fontawesome.com/4eda52b947.js"></script>
</head>
<body class="showquestions">
<#include "header.ftl">
<div class="suggestion-wrapper">
     <div class="search-container">
        <input class="input-search-question" name="query" type="text" placeholder="What are you looking for?">
        <button type="button" value="" class="search-submit positioning"></button>
    </div>
    <h1 class="unanswered-q">Unanswered Questions</h1>
    <ul class="suggestions-cont">
    <#list questionList as question>
        <li>
            <a class="respuesta-titulo" href="/question/${question.id}">${question.title}</a>
            <div class="tags-cont">
                <div class="tag-icon"><i class="fa fa-tags"></i>Categories:</div>
                <#list question.tags as tag>
                    <span class="tags"><a href="/question/tag/${tag.id}">${tag.name}</a></span>
                <#else>
                    <span class="tags">No tags</span>
                </#list>
                <div class="respuestas-total"><i class="fa fa-comments-o"></i>- <#if question.totalAnswers gt 0 && onlyOneAnswer>1<#else>${question.totalAnswers}</#if> Answer(s)</div>
                <div class="owner"><i class="fa fa-user"></i>Asked By ${(question.user.fullName)!""}</div>
            </div>
        </li>
    <#else>
        <div><@spring.message "questions.empty"/></div>
    </#list>
    </ul>
</div>


</body>

</html>
