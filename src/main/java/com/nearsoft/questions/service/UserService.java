package com.nearsoft.questions.service;

import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.auth.UserDetails;

public interface UserService {

    void save(User user);

    User getUserFromDetails(UserDetails userDetails);

    User getUserByEmail(String email);

    void updateFirstName(String firstName, User user);

    void updateLastName(String lastName, User user);

    void updateLocation(String location, User user);
}
