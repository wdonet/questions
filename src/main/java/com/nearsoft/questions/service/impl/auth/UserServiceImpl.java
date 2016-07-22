package com.nearsoft.questions.service.impl.auth;

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
        user.setPermissions(ruleService.getUserPermissions(user.getReputation()));
        return user;
    }

}
