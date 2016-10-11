<#import "/spring.ftl" as spring/>
<!DOCTYPE html>

<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="/css/general.css"/>
    <link rel="stylesheet" type="text/css" href="/css/home.css"/>
    <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css"/>
    <link href='https://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'/>

    <script src="https://use.fontawesome.com/4eda52b947.js"></script>
</head>

<body class="body-home">
<#if !user??>
    <#include "login.ftl">
<#else>
    <#include "header.ftl">
</#if>
    <h1 class="title public">${headerText}</h1>
    <section class="section-how">
        <div class="how-it-works">
            <h2 class="how-title">How It Works</h2>
            <div class="how-cont">
                <img src="img/busca-img.png" alt="Search for answers to your questions">

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
<#include "footer.ftl">
</body>


</html>
