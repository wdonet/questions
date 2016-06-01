<!DOCTYPE html>

<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
    <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.2/css/tether.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/css/drop-theme-basic.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/css/drop-theme-arrows.css"/>

    <script src="https://use.fontawesome.com/4eda52b947.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.2/js/tether.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/js/drop.min.js"></script>
    <script src="/js/search.js"></script>
</head>
<body class="showquestions">
<#include "header.ftl">
<div class="suggestion-wrapper">
    <div class="search-container">
        <input class="input-search-question" name="query" type="text" placeholder="What are you looking for?">
        <button type="button" value="" class="search-submit positioning"></button>
    </div>
    <a href="#" class="back-btn">« BACK </a>
    <div class="question-cont">
		<h1 class="unanswered-q">Notifications</h1>
		<ul>
			<li class="notifications-li"><i class="fa fa-ban close-improvement"></i>PEM_read_bio_X509 (sometimes) fails (OpenSSL 1.0.1p) <span class="close-improvement">Question closed due to improvement</span></li>
			<li class="notifications-li"><i class="fa fa-arrow-circle-up improvement"></i>Python convert date string to timestamp <span class="improvement">Question improved</span></li>
			<li class="notifications-li"><i class="fa fa-tags added-tag"></i>Using PCA for for feature extraction of OCR process <span class="added-tag">Question added for the categorie Ruby</span></li>
			<li class="notifications-li"><i class="fa fa-arrow-circle-up improvement"></i>Python convert date string to timestamp <span class="improvement">Question improved</span></li>
		</ul>
    </div>
    <#if notifications?has_content>
        <#list notifications as notification>
            <span class="tags">
                <a href="/question/tag/${notification.id}">${notification.description}</a>
            </span>
        </#list>
    </#if>
    <#if !notifications?has_content>
        <span class="tags">Nothing new for you</span>
    </#if>
</div>
<script src="/js/header.js"></script>
</body>
</html>
