<!DOCTYPE html>

<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="styles.css">
        <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
    </head>
    <body>
        <header>
            <img src="/img/logo.png" class="logo">
            <nav class="menu">
                <ul>
                    <li>FAQ</li>
                    <li>Tags</li>
                    <li><a href="/ask" class="crear-pregunta">Ask Question</a></li>
                </ul>
            </nav>
        </header>
        <section class="form-cont-ask">
            <form method="post" action="/question">
                <h1>Question</h1>
                <input name="title" type="text" placeholder="Add a title">
                <textarea name="description" type="textarea" placeholder="Add a description"></textarea>
                <h1 class="tags-title-form">Tags:</h1>
                <input name="tags" type="text" placeholder="Write a related tag">
                <input class="cancel-ask" type="button" value="Cancel" onclick="location.href='/home'">
                <input class="submit-ask" type="submit" value="Submit">
            </form>
        </section>
    </body>

</html>