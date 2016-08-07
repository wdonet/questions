<div class="suggestion-wrapper">
<#if title??>
    <h1 class="unanswered-q to-hide-when-searching">${title}</h1>
</#if>
    <ul class="suggestions-cont">
    <#if questionList??>
        <#list questionList as question>
            <li>
                <a class="answer-title" href="/question/${question.id?c}">${question.title}</a>
                <div class="tags-cont">
                    <div class="tag-icon">
                        <i class="fa fa-tags"></i>
                        Tags:
                    </div>
                    <#list question.tags as tag>
                        <span class="tags">
                            <a href="/question/tag/${tag.id?c}">${tag.name}<#if tag?has_next >,</#if></a>
                        </span>
                    <#else>
                        <span class="tags">No tags</span>
                    </#list>
                    <div class="answers-total">
                        <i class="fa fa-comments-o"></i>
                        - <#if question.totalAnswers gt 0 && onlyOneAnswer>1<#else>${question.totalAnswers}</#if> Answer(s)
                    </div>
                    <div class="owner">
                        <i class="fa fa-user"></i>Asked By
                        <img src="${question.user.photoUri!"/img/user-research-uxteam.jpg"}">
                        <span> <a href="/question/user?email=${question.user.email}">${(question.user.fullName)!""}</a></span>
                    </div>
                    <div class="date">
                        <i class="fa fa-clock-o"></i>
                        <label class="date-text">${(question.createdAt)!""}</label>
                    </div>
                </div>
            </li>
        <#else>
            <div><@spring.message "questions.empty"/></div>
        </#list>
    </#if>
    </ul>
</div>
