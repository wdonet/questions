<!DOCTYPE html>

<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="styles.css">
        <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
    </head>
    <body>
        <header>
            <img src="img/logo.png" class="logo">
            <nav class="menu">
                <ul>
                    <li>FAQ</li>
                    <li>Tags</li>
                    <li><a href="/ask" class="crear-pregunta">Ask Question</a></li>
                </ul>
            </nav>
            <head>
                <link rel="stylesheet" type="text/css" href="styles.css">
                <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
            </head>
        </header>
        <section class="form-cont">
            <h1>looking for ?</h1>
            <form method="get" action="/question/search" class="form-search-question">
                <input class="input-search-question" name="query" type="text" placeholder="Add your question here">
                <input type="submit" value="" class="search-submit">
            </form>
        </section>
        <div>
        <#include "suggestions.ftl">
        </div>
        <div><a href="/ask">Ask a new question</a></div>
    </body>
</html>
