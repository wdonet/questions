<!DOCTYPE html>

<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="/css/general.css">
        <link rel="stylesheet" type="text/css" href="/css/tags.css">
        <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
        <link href='https://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'>
        <script src="https://use.fontawesome.com/4eda52b947.js"></script>
    </head>
    <body>
    <#include "header.ftl">
        <div class="suggestion-wrapper">
            <h1 class="unanswered-questions-title"><img src="/img/tag-icon.png"> Tags</h1>
            <div class="tags-cont">
                <div>
                <#list tagList as tag>
                    <span class="tags">
                        <a href="/question/tag/${tag.id}">${tag.name}</a>
                    </span>
                <#else>
                    <span class="tags">No tags</span>
                </#list>
                </div>
            </div>
        </div>
    </body>
</html>
