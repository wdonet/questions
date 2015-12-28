package com.nearsoft.questions.controller;

import com.nearsoft.questions.controller.form.AnswerForm;
import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.error.QuestionNotFoundException;
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

    private final Logger _log = LoggerFactory.getLogger(getClass());

    @Autowired
    AnswerService _answerService;

    @Autowired
    QuestionService _questionService;

    @RequestMapping(method = RequestMethod.POST)
    public String add(@ModelAttribute AnswerForm form, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails details)
        throws QuestionNotFoundException {
        _log.debug("Who's operating ? " + details);
        if (form != null && form.getQuestionId() != null) {
            _log.debug("Trying to add " + form);
            Question question = getQuestion(form);
            Answer answer = new Answer();
            answer.setUser(getUser(details));
            answer.setDescription(form.getDescription());
            answer.setQuestion(question);
            _answerService.save(answer);
            _questionService.updateTotalAnswers(question);

            redirectAttributes.addAttribute("id", form.getQuestionId());
            return "redirect:/question/{id}";
        }
        return "redirect:/search";
    }

    private Question getQuestion(AnswerForm form) throws QuestionNotFoundException {
        Long questionId = form.getQuestionId();
        Question question = _questionService.get(questionId);
        if (question == null) {
            throw new QuestionNotFoundException(questionId);
        }
        return question;
    }
}
