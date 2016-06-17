<!DOCTYPE html>

<html lang="en">
    <head>

        <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.2/css/tether.min.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/css/drop-theme-basic.min.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/css/drop-theme-arrows.css"/>
        <link rel="stylesheet" type="text/css" href="/css/styles.css">

        <script src="https://use.fontawesome.com/4eda52b947.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.2/js/tether.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/js/drop.min.js"></script>
        <script src="/js/tags.js"></script>

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
                <#list userTagList as userTag>
                    <span class="tags">
                        <input class="tag-checkbox" value="${userTag.tag.id}" type="checkbox"
                         <#if userTag.subscribed>checked</#if>>
                         <a href="/question/tag/${userTag.tag.id}">
                         ${userTag.tag.name}</a>
                    </span>
                <#else>
                    <span class="tags">No tags</span>
                </#list>
                </div>
            </div>
        </div>
    <script src="/js/header.js"></script>
    </body>
</html>
