package com.nearsoft.questions.service;

import com.nearsoft.questions.controller.form.auth.ProfileForm;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.auth.UserDetails;

public interface UserService {

    void save(User user);

    User userFromDetails(UserDetails userDetails);

    void mergeForm(ProfileForm form, User user);

}
