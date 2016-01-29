package com.nearsoft.questions.domain;


import com.nearsoft.questions.domain.auth.User;

public interface Authored {
    User getAuthor();

    void setAuthor(User user);
}
