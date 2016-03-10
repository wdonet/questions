<#import "/spring.ftl" as spring/>
<!DOCTYPE html>

<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
    <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,700" rel="stylesheet" type="text/css">
</head>
<body>

<#include "../header.ftl">
<#include "../success.ftl">

<section class="form-cont-ask">

    <form name="update_profile" id="form_update_profile" action="/profile" method="POST" enctype="multipart/form-data">
    <@spring.message "auth.profile.firstname"/>: <input type="text" name="firstName"
                                                        value="${form.firstName}"><br/><br/>
    <@spring.message "auth.profile.lastname"/>: <input type="text" name="lastName" value="${form.lastName}"/><br/><br/>
    <@spring.message "auth.profile.location"/>: <input type="text" name="location"
                                                       value="${form.location!""}"/><br/><br/>
        <input class="submit-ask" type="submit" value="<@spring.message "update"/>">

    </form>
</section>

</body>

</html>