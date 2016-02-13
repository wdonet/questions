<!DOCTYPE html>

<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="/css/styles.css">
        <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
        <script src="/js/search.js"></script>
    </head>
    <body>
        <#include "header.ftl">
        <section class="form-cont">
            <h1>looking for ?</h1>
            <input class="input-search-question" name="query" type="text" placeholder="Add your question here">
            <input type="button" value="" class="search-submit">
            <#--<form method="get" action="/question/search" class="form-search-question">-->
            <#--</form>-->
        </section>
        <div class="suggestion-list">
        <#include "suggestions.ftl">
        </div>
        <#--<footer>-->
            <#--<ul>-->
                <#--<li><a href="/ask" class="ask-question">Ask Question</a></li>-->
            <#--</ul>-->
        <#--</footer>-->
    </body>
</html>
