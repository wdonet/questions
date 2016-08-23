<html>

<head>

    <style>

        body {
            font-family: 'Lato', sans-serif;
        }

        .headerN {
            background-color: #4F5D73;
        }
    </style>

</head>

<body>

<div align="center" style="background-color: #4F5D73;">
    <img src="http://www.nuuk.me/img/logo.png"/>
</div>
<div>
    <div align="center" class="headerN">
        <h1 class="title">Good news ${userName!}!</h1>
    </div>
    <div>
        <div align="center">
            Your question has been answered by ${userNameAnswer!}!
        </div>
        </br>
        <div align="center">
            Take your time to review it and vote it. Hope it is helpful to you!
        </div>
        </br>
        </br>
        </br>
        <div align="center">
            Question:
        </div>
        </br>
        <div align="center">
        ${questionTitle!}
        </div>
        </br>
        </br>
        </br>
        <div align="center">
            Answer:
        </div>
        </br>
        <div align="center">
        ${answerText!}
        </div>
        </br>
        <div style="margin-left: 33%">
            Thank you.
        </div>
    </div>
</div>


</body>
</html>