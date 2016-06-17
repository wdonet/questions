package com.nearsoft.questions.controller;

import com.nearsoft.questions.controller.form.AnswerForm;
import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.ItemStatus;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.error.QuestionNotFoundException;
import com.nearsoft.questions.error.UserNotOwnerOfQuestionException;
import com.nearsoft.questions.repository.AnswerRepository;
import com.nearsoft.questions.repository.AnswerRepository;
import com.nearsoft.questions.service.AnswerService;
import com.nearsoft.questions.service.QuestionService;
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
    AnswerRepository answerRepository;

    @Autowired
    AnswerService answerService;

    @Autowired
    QuestionService questionService;

    @RequestMapping(value = "/accepted", method = RequestMethod.POST)
    public String markAsAccepted(@ModelAttribute AnswerForm form, RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal UserDetails details) throws UserNotOwnerOfQuestionException {

        log.debug("Marking as accepted " + form);
        if (form == null || form.getAnswerId() == null) {
            return "redirect:/search";
        }

        answerService.markAsAccepted(form.getAnswerId(), details.getUser());
        redirectAttributes.addAttribute("id", form.getQuestionId());
        return "redirect:/question/{id}";
    }

    @RequestMapping(value = "/voteDown", method = RequestMethod.POST)
    public String voteDown(@ModelAttribute AnswerForm form, RedirectAttributes redirectAttributes) {
        log.debug("Trying to vote down " + form);
        if (form != null && form.getAnswerId() != null) {
            Answer answer = answerRepository.findOne(form.getAnswerId());
            answerService.downvote(answer);
            redirectAttributes.addAttribute("id", answer.getQuestion().getId());
            return "redirect:/question/{id}";
        }
        return "redirect:/search";
    }

    @RequestMapping(value = "/voteUp", method = RequestMethod.POST)
    public String voteUp(@ModelAttribute AnswerForm form, RedirectAttributes redirectAttributes) {
        log.debug("Trying to vote up " + form);
        if (form != null && form.getAnswerId() != null) {
            Answer answer = answerRepository.findOne(form.getAnswerId());
            answerService.upvote(answer);
            redirectAttributes.addAttribute("id", answer.getQuestion().getId());
            return "redirect:/question/{id}";
        }
        return "redirect:/search";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String add(@ModelAttribute AnswerForm form, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails details)
        throws QuestionNotFoundException {
        log.debug("Who's operating ? " + details);
        if (form != null && form.getQuestionId() != null) {
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

    private Question getQuestion(AnswerForm form) throws QuestionNotFoundException {
        Long questionId = form.getQuestionId();
        Question question = questionService.get(questionId);
        if (question == null) {
            throw new QuestionNotFoundException(questionId);
        }
        return question;
    }
}
