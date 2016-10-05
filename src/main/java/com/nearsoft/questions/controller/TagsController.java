package com.nearsoft.questions.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.domain.dto.UserTag;
import com.nearsoft.questions.service.TagsSubscriptionService;

@Controller
@RequestMapping("/tags")
public class TagsController extends BaseController {

    @Autowired
    private TagsSubscriptionService tagsSubscriptionService;

    @RequestMapping(method = RequestMethod.GET)
    public String getAllTags(Model model, @AuthenticationPrincipal UserDetails details) {
        if (details == null) {
            model.addAttribute(Collections.emptyList());
            model.addAttribute("userPermissions", Collections.emptyList());
        } else {
            User user = getUser(details);
            List<UserTag> tags = tagsSubscriptionService.getAllTagsWithSubscriptionFlagSortedByName(user);
            model.addAttribute(tags);
            model.addAttribute("userPermissions", user.getPermissions());
        }
        return "showTags";
    }
}
