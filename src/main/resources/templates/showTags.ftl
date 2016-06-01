<!DOCTYPE html>

<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="/css/styles.css">
        <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
        <script src="/js/tags.js"></script>
    </head>
    <body>
    <#include "header.ftl">
        <div class="suggestion-wrapper">
            <h1 class="unanswered-questions-title"><img src="/img/tag-icon.png"> Tags</h1>
            <div class="tags-cont">
                <div>
                <#list tagList as tag>
                    <span class="tags">
                        <input class="tag-checkbox" value="${tag.id}" type="checkbox" <#if true>checked</#if>> <a href="/question/tag/${tag.id}">${tag.name}</a>
                    </span>
                <#else>
                    <span class="tags">No tags</span>
                </#list>
                </div>
            </div>
        </div>
    </body>
</html>
