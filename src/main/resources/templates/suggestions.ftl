<#assign no_suggestions="No suggestions" >

<ul>
    <#list questions as question>
        <li>
            <span><a href="/question/${question.id}">${question.title}</a></span>
            <span>Answers ${question.totalAnswers}</span>
            <span>Tags :
                <#list question.tags as tag>
                ${tag.name} |&nbsp;
                <#else>
                    No tags
                </#list>
            </span>
        </li>
    <#else>
        <li>${no_suggestions}</li>
    </#list>
</ul>
