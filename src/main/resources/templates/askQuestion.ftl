<!DOCTYPE html>
<#assign username="nsq@nearsoft.com">

<html lang="en">
<head>
    <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="//cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/highlight.js/latest/styles/github.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.2/css/tether.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/css/drop-theme-basic.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/css/drop-theme-arrows.css"/>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="//cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
    <script src="https://cdn.jsdelivr.net/highlight.js/latest/highlight.min.js"></script>
    <script src="https://use.fontawesome.com/4eda52b947.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.2/js/tether.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/js/drop.min.js"></script>
    <script src="/js/askQuestion.js"></script>
</head>
<body>
<#include "header.ftl">
<section class="form-cont-ask">
    <form method="post" action="/question">
        <h1>Question</h1>
        <input name="title" type="text" placeholder="Add a title">
        <textarea name="description" type="textarea" placeholder="Add a description" class="source-code"></textarea>

        <h1 class="tags-title-form">Tags:</h1>
        <input name="tags" type="text" placeholder="Write a related tag">
        <#--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->
        <input class="cancel-ask" type="button" value="Cancel" onclick="location.href='/search'">
        <input class="submit-ask" type="submit" value="Submit">
        <div class="preview"><div class="markdown"></div></div>
    </form>
</section>
<script src="/js/header.js"></script>
</body>

</html>