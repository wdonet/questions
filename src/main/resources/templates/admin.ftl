<!DOCTYPE html>
<#assign username="nsq@nearsoft.com">

<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="/css/general.css"/>
    <link rel="stylesheet" type="text/css" href="/css/forms.css"/>

    <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" href="//cdn.jsdelivr.net/simplemde/latest/simplemde.min.css"/>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/highlight.js/latest/styles/github.min.css"/>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.2/css/tether.min.css"/>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/css/drop-theme-basic.min.css"/>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/css/drop-theme-arrows.css"/>
    <link rel="stylesheet" type="text/css" href="/css/styles.css"/>

    <script src="/js/analytics.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="//cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
    <script src="https://cdn.jsdelivr.net/highlight.js/latest/highlight.min.js"></script>

    <script src="https://use.fontawesome.com/4eda52b947.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.2/js/tether.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether-drop/1.4.2/js/drop.min.js"></script>
    <script src="/js/markdown.js"></script>

</head>
<body>
<#include "header.ftl">
<script src="/js/header.js"></script>
<div class="suggestion-wrapper">
    <section class="form-cont-config">

        <div class="profile-data">
            <div class="profile-picture">
                <img class="logo-config" src="/img/logo.png"/>
            </div>
            <div class="profile-info-rows">
                <form method="POST" enctype="multipart/form-data" action="/admin/upload">
                    <table>
                        <tr>
                            <td>Logo:</td>
                            <td><input type="file" name="file"/></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="submit" value="subir"/></td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

    </section>

    <section class="form-cont-ask">

        <div class="profile-data">
            <div class="profile-picture">

            </div>
            <div class="profile-info-rows">
                <div>
                    <input type="text" id="image" name="imagen" value=""
                           placeholder="imagen">
                </div>
            </div>
        </div>

    </section>


</div>
</body>

</html>