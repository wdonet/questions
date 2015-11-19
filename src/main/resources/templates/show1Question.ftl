<!DOCTYPE html>

<html lang="en">

    <body>
        <div>${question.title}</div>
        <div>${question.description}</div>
        <div>
            <#list question.tags as tag>
                &nbsp;<span>${tag.name}</span>
            <#else>
                <span>No tags</span>
            </#list>
        </div>
        <#list question.answers as answer>
            <div>${answer.description}</div>
        <#else>
            <div>No answers</div>
        </#list>
    </body>

</html>
