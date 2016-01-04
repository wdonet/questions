package com.nearsoft.questions.controller.interceptor;

import com.nearsoft.questions.domain.auth.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class UserInterceptor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

        if (modelAndView != null) {
            Optional.of(SecurityContextHolder.getContext())
                    .map(SecurityContext::getAuthentication)
                    .map(Authentication::getPrincipal)
                    .ifPresent(principal -> {
                        try {
                            UserDetails details = (UserDetails) principal;
                            modelAndView.addObject("user", details);
                            log.info("The user details for user " + details.getUsername() + " has been added to the spring model");
                        } catch (ClassCastException cce) {
                            log.info("There is no user logged in. The user object will not be added to spring model");
                        }
                    });
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
