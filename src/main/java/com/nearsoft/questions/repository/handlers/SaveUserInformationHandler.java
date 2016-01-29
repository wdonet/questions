package com.nearsoft.questions.repository.handlers;


import com.nearsoft.questions.domain.Authored;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.service.UserService;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;


@Service
@RepositoryEventHandler(Authored.class)
public class SaveUserInformationHandler {

    @Inject
    private UserService userService;

    @HandleBeforeCreate
    public void applyUserInformation(Authored authored) {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        authored.setAuthor(userService.getUserFromDetails(details));
    }
}
