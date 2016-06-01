<!DOCTYPE html>
<#assign defaultStatus="ERROR">
<#assign defaultMessage="Unkown error">

<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="/css/general.css">
        <link rel="stylesheet" type="text/css" href="/css/alerts.css">
        <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
    </head>
    <body>
        <#include "header.ftl">
        <div>
            <p>Something went wrong: </p>
            <p>${(status)!defaultStatus} : ${(message)!defaultMessage}</p>
            <p>${(stackTrace)!''}</p>
        </div>
        <br/><a href="/search">Go Home</a>
    </body>

</html>