package com.nearsoft.questions.service.impl.auth;

import com.nearsoft.questions.domain.auth.SocialMediaService;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;

import java.util.Arrays;
import java.util.List;

public class AccountConnectionSignUpService implements ConnectionSignUp {

    private final Logger log = LoggerFactory.getLogger(AccountConnectionSignUpService.class);

    private final UserRepository userRepository;
    private final Environment environment;

    public AccountConnectionSignUpService(UserRepository userRepository, Environment environment) {
        this.userRepository = userRepository;
        this.environment = environment;
    }

    public String execute(Connection<?> connection) {
        UserProfile profile = connection.fetchUserProfile();

        String domain = profile.getEmail().split("@")[1];
        if (domain != null && !checkDomain(domain)) {
            return null;
        }

        log.info("Implicit signing up. Reading the data from the provider and creating a user with this");

        User user = new User.Builder()
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .signInProvider(SocialMediaService.valueOf(connection.getKey().getProviderId().toUpperCase()))
                .build();

        userRepository.save(user);

        return user.getEmail();
    }

    private boolean checkDomain(String domain) {
        List<String> allowedDomains = Arrays.asList(environment.getProperty("spring.social.google.domains").split(","));
        return allowedDomains.contains(domain);

    }

}