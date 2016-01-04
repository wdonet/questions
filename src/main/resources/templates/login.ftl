<form name="go_signin" id="go_signin" action="/auth/google" method="POST">
    <input type="submit" value="<@spring.message "auth.signin.google"/>" />
    <input type="hidden" name="scope"
           value="profile email"/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>