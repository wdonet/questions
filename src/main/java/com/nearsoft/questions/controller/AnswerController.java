package com.nearsoft.questions.controller;

import com.nearsoft.questions.controller.form.AnswerForm;
import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.ItemStatus;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.error.OperationDeniedException;
import com.nearsoft.questions.error.QuestionNotFoundException;
import com.nearsoft.questions.repository.AnswerRepository;
import com.nearsoft.questions.service.AnswerService;
import com.nearsoft.questions.service.QuestionService;
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
    AnswerService answerService;

    @Autowired
    QuestionService questionService;

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

        Answer answer = answerService.get(form.getAnswerId());
        validateUserOwner(answer,details);

        answer.setDescription(form.getDescription());
        answerService.update(answer);

        redirectAttributes.addAttribute("id", form.getQuestionId());
        return "redirect:/question/{id}";
    }

    private void validateUserOwner(Answer answer, UserDetails details) {
        if (!answer.getUser().getId().equals(details.getUser().getId())){
            log.error("The user is not the owner of the answer, userid: {}", answer.getUser().getId());
            throw new OperationDeniedException();
        }
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
