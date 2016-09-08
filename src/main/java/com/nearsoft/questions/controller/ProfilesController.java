package com.nearsoft.questions.controller;

import com.nearsoft.questions.controller.form.auth.ProfileForm;
import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.service.AnswerService;
import com.nearsoft.questions.service.QuestionService;
import com.nearsoft.questions.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/profile")
public class ProfilesController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    public ProfilesController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Secured({"ROLE_USER"})
    public String seeMyProfile(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.debug("Rendering profile view for " + userDetails);
        User user = userService.getUserFromDetails(userDetails);
        model.addAttribute("form", new ProfileForm(user));

        setQuestionsList(model, user);

        setAnswersList(model, user);

        return "auth/profile";
    }

    @RequestMapping(method = RequestMethod.POST)
    @Secured({"ROLE_USER"})
    public String updateMyProfile(@ModelAttribute("form") ProfileForm form, @AuthenticationPrincipal UserDetails details,
                                  RedirectAttributes attributes) {
        log.info("Updating the profile for the user logged in");
        User user = userService.getUserFromDetails(details);
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setLocation(form.getLocation());
        userService.save(user);
        attributes.addFlashAttribute("successMessage", "Your profile has been updated");

        return "redirect:profile";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update/firstName")
    @Transactional
    @ResponseBody
    public void updateFirstName(@RequestParam String firstName,
                                @AuthenticationPrincipal UserDetails details) {
        log.info("Updating the profile firstName for the user logged in");
        User user = details.getUser();

        user.setFirstName(firstName.trim());

        userService.updateFirstName(user.getFirstName(), user);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/update/lastName")
    @Transactional
    @ResponseBody
    public void updateLastName(@RequestParam String lastName,
                                @AuthenticationPrincipal UserDetails details) {
        log.info("Updating the profile lastName for the user logged in");
        User user = details.getUser();

        user.setLastName(lastName.trim());

        userService.updateLastName(user.getLastName(), user);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/update/location")
    @Transactional
    @ResponseBody
    public void updateLocation(@RequestParam String location,
                                @AuthenticationPrincipal UserDetails details) {
        log.info("Updating the profile location for the user logged in");
        User user = details.getUser();

        user.setLocation(location.trim());

        userService.updateLocation(user.getLocation(), user);

    }

    private void setAnswersList(Model model, User user) {
        List<Answer> answerList = answerService.findByUser(user);

        model.addAttribute("answers", answerList);
    }

    private void setQuestionsList(Model model, User user) {
        List<Question> questionList = questionService.getNewestByCreator(user.getEmail(), 1, 0).getContent();

        model.addAttribute("questions", questionList);
    }

}
