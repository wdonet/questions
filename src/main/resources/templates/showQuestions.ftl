<#import "/spring.ftl" as spring/>
<#--<#assign title="Unanswered Questions" >-->

<!DOCTYPE html>

<html lang="en">
  <head>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
    <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'>
    <script src="https://use.fontawesome.com/4eda52b947.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="/js/search.js"></script>
  </head>
  <body class="showquestions ${pageName}">
    <#include "header.ftl">
    <#include "searchBox.ftl">
    <#include "suggestions.ftl">
  </body>
</html>
