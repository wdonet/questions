package com.nearsoft.questions.controller;

import com.nearsoft.questions.controller.form.QuestionForm;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.repository.search.QuestionSearchRepository;
import com.nearsoft.questions.service.QuestionService;
import com.nearsoft.questions.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/question")
public class QuestionsController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionSearchRepository questionSearchRepository;

    @Autowired
    TagService tagService;

    @RequestMapping(method = RequestMethod.POST)
    public String add(@ModelAttribute QuestionForm form, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails details) {
        log.debug("Who's operating ? " + details);
        if (form != null) {
            log.debug("Trying to add " + form);
            Question question = new Question(form, tagService.getPersistedTagsFromTagNameList(form.getNormalizedTagList()), getUser(details));
            questionService.save(question);
            questionSearchRepository.save(question);
            redirectAttributes.addAttribute("id", question.getId());
            return "redirect:/question/{id}";
        }
        return "redirect:/ask";
    }

    @RequestMapping(value = "/order/unanswered", method = RequestMethod.GET)
    public String getUnanswered(Model model,
                                @RequestParam(required = false, defaultValue = "1") Integer page,
                                @RequestParam(required = false, defaultValue = "0") Integer pageSize) {
        model.addAttribute("questionList", questionService.getUnanswered(page, pageSize).getContent());
        return "showQuestions";
    }

    @RequestMapping(value = "/order/newest", method = RequestMethod.GET)
    public String getNewest(Model model,
                            @RequestParam(required = false, defaultValue = "1") Integer page,
                            @RequestParam(required = false, defaultValue = "0") Integer pageSize) {
        model.addAttribute(questionService.getNewest(page, pageSize).getContent());
        return "showQuestions";
    }

    @RequestMapping(value = "/tag/{id}", method = RequestMethod.GET)
    public String getNewestByTag(Model model, @PathVariable long id,
                                 @RequestParam(required = false, defaultValue = "1") Integer page,
                                 @RequestParam(required = false, defaultValue = "0") Integer pageSize) {
        model.addAttribute(questionService.getNewestByTag(id, page, pageSize).getContent());
        return "showQuestions";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String get(@PathVariable long id, Model model) {
        log.info("question with id " + id);
        model.addAttribute(questionService.get(id));
        return "show1Question";
    }

}
