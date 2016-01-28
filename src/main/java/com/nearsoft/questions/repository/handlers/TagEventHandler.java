package com.nearsoft.questions.repository.handlers;

import com.nearsoft.questions.domain.Tag;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.service.UserService;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
@RepositoryEventHandler(Tag.class)
public class TagEventHandler {

    @Inject
    private UserService userService;

    @HandleBeforeCreate
    public void applyUserInformationUsingSecurityContext(Tag tag) {

        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        tag.setUser(userService.getUserFromDetails(details));
    }
}
