package com.nearsoft.questions.service.impl.auth;

import java.util.Arrays;
import java.util.List;
import com.nearsoft.questions.domain.auth.SocialMediaService;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;

public class AccountConnectionSignUpService implements ConnectionSignUp {

    private final Logger log = LoggerFactory.getLogger(AccountConnectionSignUpService.class);

    private final UserRepository userRepository;
    private final Environment environment;

    public AccountConnectionSignUpService(UserRepository userRepository, Environment environment) {
        this.userRepository = userRepository;
        this.environment = environment;
    }

    public String execute(Connection<?> connection) {
        UserProfile userProfile = connection.fetchUserProfile();

        String domain = userProfile.getEmail().split("@")[1];
        if (domain != null && !checkDomain(domain)) {
            return null;
        }

        log.info("Implicit signing up. Reading the data from the provider and creating a user with this");

        User user = new User.Builder()
            .firstName(userProfile.getFirstName())
            .lastName(userProfile.getLastName())
            .email(userProfile.getEmail())
            .signInProvider(SocialMediaService.valueOf(connection.getKey().getProviderId().toUpperCase()))
            .photoUri(connection.getImageUrl())
            .build();

        userRepository.save(user);

        return user.getEmail();
    }

    private boolean checkDomain(String domain) {
        String domains = environment.getProperty("spring.social.google.domains");

        if (domains != null) {
            List<String> allowedDomains = Arrays.asList(domains.split(","));
            return allowedDomains.isEmpty() || allowedDomains.contains(domain);
        }

        return true;
    }
}