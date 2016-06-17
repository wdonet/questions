<#import "/spring.ftl" as spring/>
<!DOCTYPE html>

<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="/css/general.css">
    <link rel="stylesheet" type="text/css" href="/css/home.css">
    <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'>
    <script src="https://use.fontawesome.com/4eda52b947.js"></script>
</head>

<body class="body-home">
<header>
    <#if !user??>
        <#include "login.ftl">
    <#else>
        <#include "header.ftl">
    </#if>
</header>
<h1 class="title public">All the <span>wisdom</span> of your project <span>onboarding</span> in one place</h1>
<section class="section-how">
<div class="how-it-works">
    <h2 class="how-title">How It Works</h2>
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
</div>
</section>

</body>


</html>
