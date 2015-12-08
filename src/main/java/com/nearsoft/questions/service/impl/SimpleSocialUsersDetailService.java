package com.nearsoft.questions.service.impl;

import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SimpleSocialUsersDetailService implements SocialUserDetailsService {

    private final Logger log = LoggerFactory.getLogger(SimpleSocialUsersDetailService.class);

    private final UserRepository userRepository;

    @Autowired
    public SimpleSocialUsersDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userId);

        if (user == null) {
            log.info("Username not found.");
            throw new UsernameNotFoundException("Username not found");
        }

        return new UserDetails.Builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getEmail())
                .socialSignInProvider(user.getSignInProvider())
                .role(user.getRole())
                .build();
    }

}