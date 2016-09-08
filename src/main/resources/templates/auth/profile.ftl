<#import "/spring.ftl" as spring/>
<!DOCTYPE html>

<html lang="en">
<head>

    <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet"
          type="text/css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.2/css/tether.min.css"/>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/css/drop-theme-basic.min.css"/>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/css/drop-theme-arrows.css"/>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="https://use.fontawesome.com/4eda52b947.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.2/js/tether.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/js/drop.min.js"></script>

    <link rel="stylesheet" type="text/css" href="/css/general.css">
    <link rel="stylesheet" type="text/css" href="/css/forms.css">
    <link rel="stylesheet" type="text/css" href="/css/alerts.css">
    <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet"
          type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet'
          type='text/css'>
    <script src="https://use.fontawesome.com/4eda52b947.js"></script>

</head>
<body>

<#include "../header.ftl">
<#include "../success.ftl">

<div class="suggestion-wrapper">
    <section class="form-cont-ask">

        <div class="profile-data">
            <div class="profile-picture">
                <img class="profile-profile-photo" src="${(form.photoUri)!"/img/no-photo.png"}">
            </div>
            <div class="profile-info-rows">
                <div>
                    <input type="text" id="firstName" name="firstName" value="${form.firstName}"
                           placeholder="First Name">
                </div>
                <div>
                    <input type="text" id="lastName" name="lastName" value="${form.lastName}"
                           placeholder="Last Name"/>
                </div>
                <div>
                    <input type="text" id="location" name="location" value="${form.location!""}"
                           placeholder="Location"/>
                </div>
                <div>
                <@spring.message "auth.profile.points"/>: ${form.reputation!""}
                </div>
            </div>
        </div>

    </section>

    <div id="user-activity">

        <section id="question">
            <div><@spring.message "auth.profile.myquestions"/></div>
            <div class="list">
            <#list questions as question>
                <div class="question-item">
                    <a href="/question/${question.id}">
                        <span>${question.title}</span>
                    </a>
                </div>
            </#list>
            </div>
        </section>
        <section id="answers">
            <div><@spring.message "auth.profile.myanswers"/></div>
            <div class="list">
            <#list answers as answer>
                <div class="answer-item">
                    <a href="/question/${answer.question.id}#a-${answer.id}">
                        <span>${answer.question.title}</span>
                    </a>
                </div>
            </#list>
            </div>
        </section>
    </div>
</div>
<script src="/js/header.js"></script>
<script src="/js/profile.js"></script>
</body>

</html>