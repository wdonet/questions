<!DOCTYPE html>

<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="/css/styles.css">
        <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
    </head>
    <body>
        <#include "header.ftl">
        <section class="form-cont">
            <!--hola-->
            <h1>looking for ?</h1>
            <form method="get" action="/question/search" class="form-search-question">
                <input class="input-search-question" name="query" type="text" placeholder="Add your question here">
                <input type="submit" value="" class="search-submit">
            </form>
        </section>
        <div>
        <#include "suggestions.ftl">
        </div>
        <#--<footer>-->
            <#--<ul>-->
                <#--<li><a href="/ask" class="ask-question">Ask Question</a></li>-->
            <#--</ul>-->
        <#--</footer>-->
    </body>
</html>
