package com.nearsoft.questions.service.impl.auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.nearsoft.questions.domain.RuleName;
import com.nearsoft.questions.domain.auth.Role;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.repository.UserRepository;
import com.nearsoft.questions.service.RuleService;
import com.nearsoft.questions.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private Logger log = LoggerFactory.getLogger(getClass());

    private UserRepository userRepository;

    private RuleService ruleService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RuleService ruleService) {
        this.userRepository = userRepository;
        this.ruleService = ruleService;
    }

    @Override
    public void save(User user) {
        log.info("Saving user");
        userRepository.save(user);
    }

    @Override
    public User getUserFromDetails(UserDetails userDetails) {
        return getUserByEmail(userDetails.getUsername());
    }

    @Override
    public User getUserByEmail(String email) {
        final User user = userRepository.findByEmail(email);
        user.setReputation(userRepository.getPointsForUserId(user.getId()));
        user.setPermissions(buildUserPermissions(user));
        return user;
    }

    private List<RuleName> buildUserPermissions(User user) {
        List<RuleName> userPermissions;
        if (Role.ROLE_ADMIN == user.getRole()) {
            userPermissions = new ArrayList<>();
            Collections.addAll(userPermissions, RuleName.values());
        }
        else {
            userPermissions = ruleService.getUserPermissions(user.getReputation());
        }
        return userPermissions;
    }

    @Override
    public void updateFirstName(String firstName, User user) {
        userRepository.updateFirstName(firstName, user.getId());
    }

    @Override
    public void updateLastName(String lastName, User user) {
        userRepository.updateLastName(lastName, user.getId());
    }

    @Override
    public void updateLocation(String location, User user) {
        userRepository.updateLocation(location, user.getId());
    }

}
