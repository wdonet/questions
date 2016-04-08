package com.nearsoft.questions.controller;

import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public abstract class BaseController {

    @Autowired
    UserService userService;

    protected User getUser(UserDetails details) {
        if (details == null || StringUtils.isBlank(details.getUsername())) {
            throw new UsernameNotFoundException("Invalid details: " + details);
        }
        String email = details.getUsername();
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid user: " + email);
        }
        return user;
    }

}
