package com.nearsoft.questions.controller;

import com.nearsoft.questions.controller.form.auth.ProfileForm;
import com.nearsoft.questions.domain.auth.Profile;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.service.Storage;
import com.nearsoft.questions.service.UserService;
import com.nearsoft.questions.util.FileUtils;
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

@Controller
@RequestMapping("/profile")
public class ProfilesController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService userService;
    private final Storage storage;

    @Autowired
    public ProfilesController(UserService userService, Storage storage) {
        this.userService = userService;
        this.storage = storage;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Secured({"ROLE_USER"})
    public String seeMyProfile(Model model) {
        log.info("Rendering My Profile view");

        model.addAttribute("form", new ProfileForm(userService.getUserFromDetails(
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        ));

        return "redirect:auth/profile";
    }

    @RequestMapping(method = RequestMethod.POST)
    @Secured({"ROLE_USER"})
    public String updateMyProfile(@ModelAttribute("form") ProfileForm form, @AuthenticationPrincipal UserDetails details) {
        log.info("Updating the profile for the user logged in");

        User user = userService.getUserFromDetails(details);
        Profile profile = user.getProfile();

        form.merge(form, profile);

        if (form.getPhoto() != null) {

            if (profile.getPhotoUri() != null) {
                profile.setPhotoUri(storage.replace(FileUtils.getStreamFromMultipart(
                        form.getPhoto()), profile.getPhotoUri(), form.getPhoto().getOriginalFilename()));
            } else {
                profile.setPhotoUri(storage.save(FileUtils.getStreamFromMultipart(
                        form.getPhoto()), form.getPhoto().getOriginalFilename()));
            }

        }

        userService.save(user);

        return "redirect:auth/profile";
    }

}
