package com.nearsoft.questions.controller;

import com.nearsoft.questions.controller.form.QuestionForm;
import com.nearsoft.questions.domain.ItemStatus;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.error.UserNotOwnerOfQuestionException;
import com.nearsoft.questions.repository.search.QuestionSearchRepository;
import com.nearsoft.questions.service.QuestionService;
import com.nearsoft.questions.service.TagService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionsController extends BaseController {

    public static final String SHOW_QUESTIONS = "showQuestions";
    public static final String TITLE = "title";
    public static final String PAGE_NAME = "pageName";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionSearchRepository questionSearchRepository;

    @Autowired
    TagService tagService;

    @Value("${questions.onlyOneAnswer}")
    private Boolean onlyOneAnswer;

    @RequestMapping(method = RequestMethod.POST)
    public String add(@ModelAttribute QuestionForm form, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails details) {
        log.debug("Who's operating ? " + details);
        if (form != null) {
            log.debug("Trying to add " + form);
            Question question = new Question(form,
                tagService.getPersistedTagsFromTagNameList(form.getNormalizedTagList()), getUser(details));
            question.setStatus(ItemStatus.OPEN);
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
        model.addAttribute(TITLE, "Unanswered Questions");
        model.addAttribute(PAGE_NAME, "unanswered");
        model.addAttribute("onlyOneAnswer", onlyOneAnswer);
        return SHOW_QUESTIONS;
    }

    @RequestMapping(value = "/order/newest", method = RequestMethod.GET)
    public String getNewest(Model model,
                            @RequestParam(required = false, defaultValue = "1") Integer page,
                            @RequestParam(required = false, defaultValue = "0") Integer pageSize) {
        model.addAttribute(questionService.getNewest(page, pageSize).getContent());
        model.addAttribute(TITLE, "Newest Questions");
        model.addAttribute(PAGE_NAME, "newest");
        model.addAttribute("onlyOneAnswer", onlyOneAnswer);
        return SHOW_QUESTIONS;
    }

    @RequestMapping(value = "/tag/{id}", method = RequestMethod.GET)
    public String getNewestByTag(Model model, @PathVariable long id,
                                 @RequestParam(required = false, defaultValue = "1") Integer page,
                                 @RequestParam(required = false, defaultValue = "0") Integer pageSize) {
        List<Question> questionList = questionService.getNewestByTag(id, page, pageSize).getContent();
        model.addAttribute(questionList);
        if (CollectionUtils.isNotEmpty(questionList)) {
            String tagName =
                questionList.stream().findFirst().get().getTags().stream().filter(tag -> tag.getId().equals(id)).findFirst().get().getName();
            model.addAttribute(TITLE, "Questions for " + tagName);
        }
        else {
            model.addAttribute(TITLE, "");
        }
        model.addAttribute(PAGE_NAME, "tagged");
        model.addAttribute("onlyOneAnswer", onlyOneAnswer);
        return SHOW_QUESTIONS;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String get(@PathVariable long id, Model model, @AuthenticationPrincipal UserDetails details) {
        log.info("question with id " + id);

        Question question = questionService.get(id);
        model.addAttribute("isQuestionOwner", details.getUser().getId().equals(question.getUser().getId()));
        model.addAttribute("isAlreadyAccepted", question.getStatus() == ItemStatus.ACCEPTED);
        model.addAttribute(question);
        model.addAttribute("onlyOneAnswer", onlyOneAnswer);
        return "showOneQuestion";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public List<Question> search(@RequestParam String query) {
        log.debug("Query : " + query);
        return questionService.search(query);
    }

}
