<#import "/spring.ftl" as spring/>
<!DOCTYPE html>

<html lang="en">

<body>
<form name="go_signin" id="go_signin" action="/auth/google" method="POST">
    <button type="submit"><@spring.message "auth.signin.google"/></button>
    <input type="hidden" name="scope"
           value="profile email"/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

</body>

</html>