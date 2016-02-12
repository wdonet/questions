<#import "/spring.ftl" as spring/>
<!DOCTYPE html>

<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
    <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
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
        It's easy to find answers to <span>internal</span> & <span>technical</span> questions
    </h1>
    <!--     It's not easy to find answers to internal and technical questions inside a team project, as well as questions
        regarding internal process of the company. -->


    <section class="how-it-works">
        <div class="how-cont">
            <img src="img/busca-img.png" alt="Busca alguna pregunta">

            <h1>Search Questions</h1>

            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod.</p>
        </div>
        <div class="how-cont">
            <img src="img/crea-img.png" alt="Crea tu pregunta">

            <h1>Create Questions</h1>

            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod.</p>
        </div>
        <div class="how-cont">
            <img src="img/notifica-img.png" alt="Notifica">

            <h1>Notify Questions</h1>

            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod.</p>
        </div>
    </section>
</div>


</body>


</html>
