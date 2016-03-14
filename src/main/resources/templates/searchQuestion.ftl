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

    <div class="search-container">
        <input class="input-search-question" name="query" type="text" placeholder="What are you looking for?">
        <input type="button" value="" class="search-submit">
    </div>
</section>
<div class="suggestion-list">
<#include "suggestions.ftl">
</div>
</body>
</html>
