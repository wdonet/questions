package com.nearsoft.questions.controller;

import javax.websocket.server.PathParam;

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
import com.nearsoft.questions.service.QuestionCommentService;

@Controller
@RequestMapping("/comments")
public class CommentsController extends BaseController {

	@Autowired
	private QuestionCommentService questionCommentService;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="question/{id}", method= RequestMethod.GET)
	public String getCommentForm(@PathVariable("id") final Long sourceId, final Model model){
		model.addAttribute("sourceId", sourceId);
		return "commentForm";
	}

    @RequestMapping(value={"/question/{id}", "/question"}, method = RequestMethod.POST)
    public String add(@ModelAttribute CommentForm form, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails details) {
        log.debug("Who's operating ? " + details);
        if (form != null) {
            log.debug("Trying to add " + form);
            questionCommentService.save(form, getUser(details));
            redirectAttributes.addAttribute("id", form.getSourceId());
            return "redirect:/question/{id}";
        }
        return "redirect:/ask";
    }
}
