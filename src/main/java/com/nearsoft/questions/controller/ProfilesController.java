package com.nearsoft.questions.controller;

import com.nearsoft.questions.controller.form.auth.ProfileForm;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.auth.UserDetails;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfilesController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService userService;

    @Autowired
    public ProfilesController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Secured({"ROLE_USER"})
    public String seeMyProfile(Model model) {
        log.info("Rendering My Profile view");

        model.addAttribute("form", new ProfileForm(userService.getUserFromDetails(
            (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        ));

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
}
