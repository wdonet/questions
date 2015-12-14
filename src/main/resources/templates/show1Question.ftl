<!DOCTYPE html>

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
                &nbsp;<span>${tag.name}</span>
            <#else>
                <span>No tags</span>
            </#list>
        </div>
        <#list question.answers as answer>
            <div>${answer.description}</div>
        <#else>
            <div>No answers</div>
        </#list>
    </body>

</html>