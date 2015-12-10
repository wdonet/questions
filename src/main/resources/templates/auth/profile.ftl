<#import "/spring.ftl" as spring/>
<!DOCTYPE html>

<html lang="en">

<body>
<form name="update_profile" id="form_update_profile" action="/profile" method="POST" enctype="multipart/form-data">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    First name: <input type="text" name="firstName" value="${form.firstName}"><br/><br/>
    Last name: <input type="text" name="lastName" value="${form.lastName}"/><br/><br/>
    Location: <input type="text" name="location" value="${form.location!""}"/><br/><br/>
    Photo: <input type="file" name="photo"/><br/><br/>

    <button type="submit"><@spring.message "update"/></button>
</form>

</body>

</html>