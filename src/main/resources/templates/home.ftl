<#import "/spring.ftl" as spring/>
<!DOCTYPE html>

<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="/css/general.css">
    <link rel="stylesheet" type="text/css" href="/css/home.css">
    <link rel="stylesheet" type="text/css" href="/css/searchForm.css">

    <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.2/css/tether.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/css/drop-theme-basic.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/css/drop-theme-arrows.css"/>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">

    <script src="https://use.fontawesome.com/4eda52b947.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.2/js/tether.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/js/drop.min.js"></script>
</head>

<body class="body-home">
<header>
    <#if !user??>
        <#include "login.ftl">
    <#else>
        <#include "header.ftl">
    </#if>
    </div>
</header>

<div class="suggestion-wrapper">
    <h1 class="title">
        Search And Find Questions
    </h1>
    <div class="search-container">
        <input class="input-search-question" name="query" type="text" placeholder="What are you looking for?">
        <button type="button" value="" class="search-submit positioning"></button>
        <!-- <i class="fa fa-search positioning"></i> -->
        <p class="nuuk-tip">Quick Tip On How to Search: ( Ex.-  Cómo hacer una declaración de impuestos? ).</p>
    </div>
</div>

<section class="section-how">
<div class="how-it-works">
    <h2 class="how-title">How It Works</h2>
    <div class="how-cont">
        <img src="img/busca-img.png" alt="Busca alguna pregunta">

        <h1>Search Questions</h1>

        <p>Find out how the most common problems within your project have been solved.</p>
    </div>
    <div class="how-cont">
        <img src="img/crea-img.png" alt="Crea tu pregunta">

        <h1>Create Questions</h1>

        <p>Contribute to your team by creating useful questions that will help others.</p>
    </div>
    <div class="how-cont">
        <img src="img/notifica-img.png" alt="Notifica">

        <h1>Notify Questions</h1>

        <p>Get realtime input regarding questions of your interest.</p>
    </div>
</div>
</section>
<#if user??>
    <script src="/js/header.js"></script>
</#if>
</body>


</html>
