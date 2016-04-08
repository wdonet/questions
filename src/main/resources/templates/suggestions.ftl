<#assign no_suggestions="No suggestions" >

<div class="suggestion-wrapper">
    <ul class="suggestions-cont">
        <#--<#list questions as question>-->
            <#--<li>-->
                <#--<a class="respuesta-titulo" href="/question/${question.id}">${question.title}</a>-->
                <#--<div class="respuestas-total">- ${question.totalAnswers} Answers</div>-->
                <#--<div class="tags-cont">-->
                    <#--<div class="tag-icon"><img src="img/tag-icon.png" alt="">Tags:</div>-->
                    <#--<#list question.tags as tag>-->
                        <#--<span class="tags"><a href="/question/tag/${tag.id}">${tag.name}</a></span>-->
                    <#--<#else>-->
                        <#--<span class="tags">No tags</span>-->
                    <#--</#list>-->
                    <#--<div class="owner">asked by &nbsp; <span>${(question.user.fullName)!""}</span></div>-->
                <#--</div>-->
            <#--</li>-->
        <#--<#else>-->
            <#--<li>${no_suggestions}</li>-->
        <#--</#list>-->
    </ul>
</div>
