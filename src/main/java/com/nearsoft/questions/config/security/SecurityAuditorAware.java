package com.nearsoft.questions.config.security;

import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.service.UserService;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class SecurityAuditorAware implements AuditorAware<User> {

    @Inject
    private UserService userService;

    public User getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return ((UserDetails) authentication.getPrincipal()).getUser();
//        return userService.getUserByEmail("nsq@nearsoft.com");
    }
}
