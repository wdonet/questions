<!DOCTYPE html>

<html lang="en">

    <body>
        <form method="get" action="/question/search">
            <br>Title:
            <br><input name="query" type="text">
            <input type="submit" value="Submit">
            <div>
            <#include "suggestions.ftl">
            </div>
        </form>
        <div><a href="/ask">Ask a new question</a></div>
    </body>
</html>
