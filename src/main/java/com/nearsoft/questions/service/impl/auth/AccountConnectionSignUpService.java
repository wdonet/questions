package com.nearsoft.questions.service.impl.auth;

import com.nearsoft.questions.domain.auth.SocialMediaService;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;

public class AccountConnectionSignUpService implements ConnectionSignUp {

    private final Logger log = LoggerFactory.getLogger(AccountConnectionSignUpService.class);

    private final UserRepository userRepository;

    public AccountConnectionSignUpService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String execute(Connection<?> connection) {
        UserProfile profile = connection.fetchUserProfile();

        log.info("Implicit signing up. Reading the data from the provider and creating a user with this");

        //TODO: @imarban Verify the email from google belongs to a defined domain
//        if (profile.getEmail().split("@").length != 2 || !profile.getEmail().split("@")[1].equals("nearsoft.com")) {
//            return null;
//        }

        User user = new User.Builder()
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .signInProvider(SocialMediaService.valueOf(connection.getKey().getProviderId().toUpperCase()))
                .build();

        userRepository.save(user);

        return user.getEmail();
    }

}