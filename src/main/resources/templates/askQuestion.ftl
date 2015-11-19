<!DOCTYPE html>

<html lang="en">

    <body>
        <form method="post" action="/question">
            <br>Title:
            <br><input name="title" type="text">
            <br>
            <br><textarea name="description"></textarea>
            <br>Tags
            <br><input name="tags" type="text">
            <br><input type="button" value="Cancel" onclick="location.href='/home'">
            <input type="submit" value="Submit">
        </form>
    </body>

</html>