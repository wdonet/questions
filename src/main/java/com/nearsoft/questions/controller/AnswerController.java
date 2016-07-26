package com.nearsoft.questions.controller;

import com.nearsoft.questions.controller.form.AnswerForm;
import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.ItemStatus;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.RuleName;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.error.OperationDeniedException;
import com.nearsoft.questions.error.QuestionNotFoundException;
import com.nearsoft.questions.service.AnswerService;
import com.nearsoft.questions.service.QuestionService;
import com.nearsoft.questions.service.RuleService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/answer")
public class AnswerController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RuleService ruleService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    @RequestMapping(method = RequestMethod.POST)
    public String add(@ModelAttribute AnswerForm form, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails details)
        throws QuestionNotFoundException {
        log.debug("Who's operating ? " + details);
        if (form != null && form.getQuestionId() != null && StringUtils.isNotBlank(form.getDescription())) {
            log.debug("Trying to add " + form);
            Question question = getQuestion(form);
            Answer answer = new Answer();
            answer.setUser(getUser(details));
            answer.setDescription(form.getDescription());
            answer.setQuestion(question);
            answer.setStatus(ItemStatus.NOT_ACCEPTED);
            answerService.save(answer);

            redirectAttributes.addAttribute("id", form.getQuestionId());
            return "redirect:/question/{id}";
        }
        return "redirect:/search";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@ModelAttribute AnswerForm form, RedirectAttributes redirectAttributes,
                         @AuthenticationPrincipal UserDetails details) throws QuestionNotFoundException {

        log.debug("Who's operating ? " + details);
        log.debug("Trying to add " + form);
        if (form == null || form.getQuestionId() == null) {
            return "redirect:/search";
        }

        User user = getUser(details);
        Answer answer = answerService.get(form.getAnswerId());
        validateAuthorizedUser( answer, user );

        answer.setDescription(form.getDescription());
        answerService.update(answer);

        redirectAttributes.addAttribute("id", form.getQuestionId());
        return "redirect:/question/{id}";
    }

    private void validateAuthorizedUser( Answer answer, User user) {

        if ( canEditAnswer( answer, user ) ){
            return;
        }

        log.error("The user is not authorized for performing this operation, userid: {}", answer.getUser().getId() );
        throw new OperationDeniedException();
    }

    private boolean canEditAnswer( Answer answer, User user ) {
        return isUserOwner( answer, user) ||
                ruleService.isValidUserPermission( RuleName.EDIT_OTHER_ANSWERS, user );
    }

    private boolean isUserOwner(Answer answer, User user) {
        return answer.getUser().getId().equals(user.getId());
    }

    private Question getQuestion(AnswerForm form) throws QuestionNotFoundException {
        Long questionId = form.getQuestionId();
        Question question = questionService.get(questionId);
        if (question == null) {
            throw new QuestionNotFoundException(questionId);
        }
        return question;
    }
}
