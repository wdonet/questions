<!DOCTYPE html>
<#assign defaultStatus="ERROR">
<#assign defaultMessage="Unkown error">

<html lang="en">
    <head>
        <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.2/css/tether.min.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/css/drop-theme-basic.min.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/css/drop-theme-arrows.css"/>
        <link rel="stylesheet" type="text/css" href="/css/styles.css">

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
        <script src="https://use.fontawesome.com/4eda52b947.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.2/js/tether.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/js/drop.min.js"></script>
    </head>
    <body>
        <#include "header.ftl">
        <div>
            <p>Something went wrong: </p>
            <p>${(status)!defaultStatus} : ${(message)!defaultMessage}</p>
            <p>${(stackTrace)!''}</p>
        </div>
        <br/><a href="/search">Go Home</a>
        <script src="/js/header.js"></script>
    </body>

</html>