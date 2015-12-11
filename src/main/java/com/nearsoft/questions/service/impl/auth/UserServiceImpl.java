package com.nearsoft.questions.service.impl.auth;

import com.nearsoft.questions.controller.form.auth.ProfileForm;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.auth.UserDetails;
import com.nearsoft.questions.repository.UserRepository;
import com.nearsoft.questions.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        log.info("Saving user");
        userRepository.save(user);
    }

    @Override
    public User userFromDetails(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername());
    }

}
