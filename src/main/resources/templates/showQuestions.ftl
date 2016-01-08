<!DOCTYPE html>

<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="/css/styles.css">
        <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
    </head>
    <body>
<!--     <#include "header.ftl">
    <#list questionList as question>
        <div>
            <span><a href="/question/${question.id}">${question.title}</a></span>
            <span> - </span>
            <span>${question.totalAnswers} answers .</span>
        </div>
        <div>
            <#list question.tags as tag>
                &nbsp;
                <span>${tag.name}</span>
            <#else>
                <span>No tags</span>
            </#list>
        </div>
    </#list> -->


        <div class="suggestion-wrapper">
            <ul class="suggestions-cont">
                <#include "header.ftl">
                <#list questionList as question>
                    <li>
                        <a class="respuesta-titulo" href="/question/${question.id}">${question.title}</a>
                        <div class="respuestas-total">- ${question.totalAnswers} Answers</div>
                        <div class="tags-cont">
                            <div class="tag-icon"><img src="img/tag-icon.png" alt="">Tags:</div>
                            <#list question.tags as tag>
                                <span class="tags">${tag.name}</span>
                            <#else>
                                <span class="tags">No tags</span>
                            </#list>
                        </div>
                    </li>
                </#list>
            </ul>
        </div>



    </body>

</html>
