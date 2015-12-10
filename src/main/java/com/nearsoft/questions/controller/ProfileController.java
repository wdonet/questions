package com.nearsoft.questions.controller;

import com.nearsoft.questions.controller.form.auth.ProfileForm;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Secured({"ROLE_USER"})
    public String seeMyProfile(Model model) {
        log.info("Rendering My Profile view");

        model.addAttribute("form", new ProfileForm(userService.userFromDetails(
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        ));

        return "auth/profile";
    }

    @RequestMapping(method = RequestMethod.POST)
    @Secured({"ROLE_USER"})
    public String updateMyProfile(@ModelAttribute("form") ProfileForm form) {
        log.info("Updating the profile for the user logged in");

        User user = userService.userFromDetails(
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        userService.mergeForm(form, user);
        userService.save(user);

        return "auth/profile";
    }

}
