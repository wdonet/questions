<!DOCTYPE html>

<html lang="en">

<body>
<form name="go_signin" id="go_signin" action="/auth/google" method="POST">
    <button type="submit">Sign In with Google</button>
    <input type="hidden" name="scope"
           value="https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo#email https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/tasks https://www-opensocial.googleusercontent.com/api/people"/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

</body>

</html>