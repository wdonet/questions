<#import "/spring.ftl" as spring/>
<#--<#assign title="Unanswered Questions" >-->

<!DOCTYPE html>

<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="/css/general.css"/>
        <link rel="stylesheet" type="text/css" href="/css/showQuestion.css"/>
        <link rel="stylesheet" type="text/css" href="/css/searchForm.css"/>
        <link rel="stylesheet" type="text/css" href="/css/searchResults.css"/>
        <link rel="stylesheet" type="text/css" href="/css/tags.css"/>

        <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css"/>
        <link href='https://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.2/css/tether.min.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/css/drop-theme-basic.min.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/css/drop-theme-arrows.css"/>
        <link rel="stylesheet" type="text/css" href="/css/styles.css"/>

        <script src="/js/analytics.js"></script>

        <script src="https://use.fontawesome.com/4eda52b947.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.2/js/tether.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/js/drop.min.js"></script>
        <script src="/js/search.js"></script>
    </head>
    <body class="showquestions ${pageName}">
        <#if !user??>
            <#include "login.ftl">
        <#else>
            <#include "header.ftl">
        </#if>
        <#include "searchBox.ftl">
        <#include "suggestions.ftl">
        <script src="/js/header.js"></script>
    </body>
</html>
