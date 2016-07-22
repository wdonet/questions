package com.nearsoft.questions.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nearsoft.questions.controller.form.CommentForm;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.service.AnswerService;
import com.nearsoft.questions.service.CommentService;

@Controller
@RequestMapping("/comments")
public class CommentsController extends BaseController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AnswerService answerService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "question/{id}", method = RequestMethod.GET)
    public String getQuestionCommentForm(@PathVariable("id") final Long sourceId, final Model model) {
        model.addAttribute("sourceId", sourceId);
        return "commentForm";
    }

    @RequestMapping(value = {"/question/{id}"}, method = RequestMethod.POST)
    public String saveQuestionComment(RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails details,
        @ModelAttribute CommentForm form, @PathVariable("id") Long questionId) {
        log.debug("Who's operating ? " + details);
        if (form != null && questionId > 0 && StringUtils.isNotBlank(form.getDescription())) {
            log.debug("Trying to add comment for question " + questionId );
            form.setSourceId(questionId);
            commentService.addToQuestion(form, getUser(details));
        }
        redirectAttributes.addAttribute("id", questionId);
        return "redirect:/question/{id}";
    }

    @RequestMapping(value = "answer/{id}", method = RequestMethod.GET)
    public String getAnswerCommentForm(@PathVariable("id") final Long sourceId, final Model model) {
        model.addAttribute("sourceId", sourceId);
        return "commentForm";
    }

    @RequestMapping(value = {"/question/{questionId}/answer/{answerId}", "/answer"}, method = RequestMethod.POST)
    public String saveAnswerComment(RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails details,
        @ModelAttribute CommentForm form, @PathVariable("questionId") Long questionId, @PathVariable("answerId") Long answerId) {
        log.debug("Who's operating ? " + details);
        if (form != null && answerId > 0 && StringUtils.isNotBlank(form.getDescription())) {
            log.debug("Trying to add comment for answer " + answerId);
            form.setSourceId(answerId);
            commentService.addToAnswer(form, getUser(details));
        }
        redirectAttributes.addAttribute("id", questionId);
        return "redirect:/question/" + questionId;
    }
}
