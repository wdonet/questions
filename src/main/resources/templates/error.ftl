<!DOCTYPE html>
<#assign defaultStatus="ERROR">
<#assign defaultMessage="Unkown error">

<html lang="en">

    <body>
        Something went wrong: ${(data.status)!defaultStatus} ${(data.message)!defaultMessage}
        <br/><a href="/home">Go Home</a>
    </body>

</html>