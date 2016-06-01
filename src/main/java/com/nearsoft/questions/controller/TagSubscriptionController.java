package com.nearsoft.questions.controller;

import com.nearsoft.questions.domain.Tag;
import com.nearsoft.questions.domain.TagSubscription;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.error.TagNotFoundException;
import com.nearsoft.questions.error.UserNotSubscribedToTagException;
import com.nearsoft.questions.service.TagsSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class TagSubscriptionController extends BaseController {

    @Autowired
    TagsSubscriptionService tagsSubscriptionService;

    @RequestMapping(value = "/subscription/tags/{tagId}", method = RequestMethod.POST)
    public void subscribe(@PathVariable Long tagId, @AuthenticationPrincipal UserDetails details) throws TagNotFoundException {

        User user = getUser(details);

        tagsSubscriptionService.subscribe(user, tagId);
    }

    @RequestMapping(value = "/subscription/tags/{tagId}", method = RequestMethod.DELETE)
    public void unsubscribe(@PathVariable Long tagId, @AuthenticationPrincipal UserDetails details) throws UserNotSubscribedToTagException, TagNotFoundException {

        User user = getUser(details);

        tagsSubscriptionService.unsubscribe(user, tagId);

    }

    @RequestMapping(value = "/subscription/tags/{tagId}", method = RequestMethod.GET)
    public boolean isSubscribed(@PathVariable Long tagId, @AuthenticationPrincipal UserDetails details) {
        User user = getUser(details);

        return tagsSubscriptionService.isSubscribed(user, tagId);
    }

    @RequestMapping(value = "/subscription/tags/", method = RequestMethod.GET)
    public List<Tag> getTagsSubscribed(@AuthenticationPrincipal UserDetails details) {
        User user = getUser(details);

        return tagsSubscriptionService.findTagsByUser(user);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TagNotFoundException.class)
    public String handleControllerException(HttpServletResponse response, TagNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotSubscribedToTagException.class)
    public String handleControllerException(HttpServletResponse response, UserNotSubscribedToTagException ex) {
        return ex.getMessage();
    }

}
