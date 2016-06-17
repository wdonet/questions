<div class="suggestion-wrapper">
<#if title??>
    <h1 class="unanswered-q">${title}</h1>
</#if>
    <ul class="suggestions-cont">
    <#list questionList as question>
        <li>
            <a class="answer-title" href="/question/${question.id}">${question.title}</a>
            <div class="tags-cont">
                <div class="tag-icon">
                    <i class="fa fa-tags"></i>
                    Categories:
                </div>
                <#list question.tags as tag>
                    <span class="tags">
                        <a href="/question/tag/${tag.id}">${tag.name}</a>
                    </span>
                <#else>
                    <span class="tags">No tags</span>
                </#list>
                <div class="answers-total">
                    <i class="fa fa-comments-o"></i>
                    - <#if question.totalAnswers gt 0 && onlyOneAnswer>1<#else>${question.totalAnswers}</#if> Answer(s)
                </div>
                <div class="owner">
                    <i class="fa fa-user"></i>
                    Asked By ${(question.user.fullName)!""}</div>
            </div>
        </li>
    <#else>
        <div><@spring.message "questions.empty"/></div>
    </#list>
    </ul>
</div>
