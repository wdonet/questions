package com.nearsoft.questions.controller;

import com.nearsoft.questions.controller.BaseController;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.domain.dto.UserTag;
import com.nearsoft.questions.service.TagService;
import com.nearsoft.questions.service.TagsSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/tags")
public class TagsController extends BaseController {

    @Autowired
    TagsSubscriptionService tagsSubscriptionService;

    @RequestMapping(method = RequestMethod.GET)
    public String getAllTags(Model model, @AuthenticationPrincipal UserDetails details) {
        User user = getUser(details);

        List<UserTag> tags = tagsSubscriptionService.getAllTagsWithSubscriptionFlagSortedByName(user);

        model.addAttribute(tags);

        return "showTags";
    }
}
