package com.nearsoft.questions.controller;

import com.nearsoft.questions.controller.form.QuestionForm;
import com.nearsoft.questions.domain.ItemStatus;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.RuleName;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.error.OperationDeniedException;
import com.nearsoft.questions.service.AnswerService;
import com.nearsoft.questions.service.QuestionService;
import com.nearsoft.questions.service.RuleService;
import com.nearsoft.questions.service.TagService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public static final String ONLY_ONE_ANSWER = "onlyOneAnswer";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private TagService tagService;

    @Autowired
    private RuleService ruleService;

    @RequestMapping(value = "/{id}/voteDown", method = RequestMethod.POST)
    public String voteDown(@PathVariable("id") Long questionId, RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal UserDetails details) {
        log.debug("Trying to vote down Question " + questionId);
        if (questionId != null && questionId > 0) {
            questionService.downVote(questionId, getUser(details));
            redirectAttributes.addAttribute("id", questionId);
            return "redirect:/question/{id}";
        }
        return "redirect:/search";
    }

    @RequestMapping(value = "/{id}/voteUp", method = RequestMethod.POST)
    public String voteUp(@PathVariable("id") Long questionId, RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal UserDetails details) {
        log.debug("Trying to vote up Question " + questionId);
        if (questionId != null && questionId > 0) {
            questionService.upVote(questionId, getUser(details));
            redirectAttributes.addAttribute("id", questionId);
            return "redirect:/question/{id}";
        }
        return "redirect:/search";
    }

    @RequestMapping(value = "/{id}/answer/{answerId}/voteDown", method = RequestMethod.POST)
    public String voteDown(@PathVariable("id") Long questionId, @PathVariable("answerId") Long answerId, RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal UserDetails details) {
        log.debug(String.format("Trying to vote down Answer %d of Question %d", answerId, questionId));
        if (answerId != null && answerId > 0) {
            answerService.downVote(answerId, getUser(details));
            redirectAttributes.addAttribute("id", questionId);
            return "redirect:/question/{id}";
        }
        return "redirect:/search";
    }

    @RequestMapping(value = "/{id}/answer/{answerId}/voteUp", method = RequestMethod.POST)
    public String voteUp(@PathVariable("id") Long questionId, @PathVariable("answerId") Long answerId, RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal UserDetails details) {
        log.debug(String.format("Trying to vote up Answer %d of Question %d", answerId, questionId));
        if (answerId != null && answerId > 0) {
            answerService.upVote(answerId, getUser(details));
            redirectAttributes.addAttribute("id", questionId);
            return "redirect:/question/{id}";
        }
        return "redirect:/search";
    }

    @RequestMapping(value = "/{id}/answer/{answerId}/accept", method = RequestMethod.POST)
    public String markAsAccepted(@PathVariable("id") Long questionId, @PathVariable("answerId") Long answerId, RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal UserDetails details) {

        log.debug("Accepting answer " + answerId);
        if (answerId == null || answerId <= 0) {
            return "redirect:/search";
        }

        answerService.markAsAccepted(answerId, details.getUser());
        redirectAttributes.addAttribute("id", questionId);
        return "redirect:/question/{id}";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String add(@ModelAttribute QuestionForm form, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails details) {
        log.debug("Who's operating ? " + details);
        if (form != null) {
            log.debug("Trying to add " + form);
            Question question = new Question(form,
                tagService.getPersistedTagsFromTagNameList(form.getNormalizedTagList(true)), getUser(details));
            question.setStatus(ItemStatus.OPEN);
            questionService.save(question);
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
        model.addAttribute(ONLY_ONE_ANSWER, configurationService.getBoolean("show_only_one_answer", false));
        return SHOW_QUESTIONS;
    }

    @RequestMapping(value = "/order/newest", method = RequestMethod.GET)
    public String getNewest(Model model,
                            @RequestParam(required = false, defaultValue = "1") Integer page,
                            @RequestParam(required = false, defaultValue = "0") Integer pageSize) {
        model.addAttribute(questionService.getNewest(page, pageSize).getContent());
        model.addAttribute(TITLE, "Newest Questions");
        model.addAttribute(PAGE_NAME, "newest");
        model.addAttribute(ONLY_ONE_ANSWER, configurationService.getBoolean("show_only_one_answer", false));
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
        model.addAttribute(ONLY_ONE_ANSWER, configurationService.getBoolean("show_only_one_answer", false));
        return SHOW_QUESTIONS;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getNewestByUser(Model model, @RequestParam String email,
                                 @RequestParam(required = false, defaultValue = "1") Integer page,
                                 @RequestParam(required = false, defaultValue = "0") Integer pageSize) {
        List<Question> questionList = questionService.getNewestByCreator(email, page, pageSize).getContent();
        model.addAttribute(questionList);
        model.addAttribute(TITLE, "Questions by " + email);
        model.addAttribute(PAGE_NAME, "email");
        model.addAttribute(ONLY_ONE_ANSWER, configurationService.getBoolean("show_only_one_answer", false));
        return SHOW_QUESTIONS;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String get(@PathVariable long id, Model model, @AuthenticationPrincipal UserDetails details) {
        log.info("question with id " + id);

        Question question = questionService.get(id);
        User user = getUser(details);
        model.addAttribute("isQuestionOwner", user.getId().equals(question.getUser().getId()));
        model.addAttribute("isNotClosed", question.getStatus() != ItemStatus.CLOSED);
        model.addAttribute("isAlreadyAccepted", question.getStatus() == ItemStatus.ACCEPTED);
        model.addAttribute("userId", user.getId());
        model.addAttribute("userPermissions", user.getPermissions());
        model.addAttribute(question);
        model.addAttribute(ONLY_ONE_ANSWER, configurationService.getBoolean("show_only_one_answer", false));
        return "showOneQuestion";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public List<Question> search(@RequestParam String query) {
        log.debug("Query : " + query);
        return questionService.search(query);
    }

    @RequestMapping( value = "/update", method = RequestMethod.POST)
    public String update(@ModelAttribute QuestionForm form, RedirectAttributes redirectAttributes,
                         @AuthenticationPrincipal UserDetails details) {

        log.debug("Update question request with form:" + form);
        if (form == null) {
            return "redirect:/ask";
        }

        Question questionRegister = questionService.get(form.getId());
        User user = userService.getUserByEmail(questionRegister.getUser().getEmail());
        validateAuthorizedUser(questionRegister, user);

        Question question = new Question(
                form, tagService.getPersistedTagsFromTagNameList(form.getNormalizedTagList(true)), user);
        question.setStatus(questionRegister.getStatus());
        questionService.update(question);

        redirectAttributes.addAttribute("id", question.getId());
        return "redirect:/question/{id}";
    }


    private void validateAuthorizedUser(Question question, User user) {

        if ( canEditQuestion(question, user) ){
           return;
        }

        log.error("The user is not authorized for performing this operation, userid: {}", question.getUser().getId());
        throw new OperationDeniedException();
    }

    private boolean canEditQuestion(Question question, User user) {
        return isUserOwner(question, user) ||
                ruleService.isValidUserPermission( RuleName.EDIT_OTHER_QUESTIONS, user );
    }

    private boolean isUserOwner(Question question, User user) {
        return question.getUser().getId().equals(user.getId());
    }

}
