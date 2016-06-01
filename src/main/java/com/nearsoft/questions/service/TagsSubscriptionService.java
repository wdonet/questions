package com.nearsoft.questions.service;

import com.nearsoft.questions.domain.Tag;
import com.nearsoft.questions.domain.TagSubscription;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.error.TagNotFoundException;
import com.nearsoft.questions.error.UserNotSubscribedToTagException;

import java.util.List;

public interface TagsSubscriptionService {
    void subscribe(User user, Long tagId) throws TagNotFoundException;

    void unsubscribe(User user, Long tagId) throws TagNotFoundException, UserNotSubscribedToTagException;

    boolean isSubscribed(User user, Long tagId);

    List<Tag> findTagsByUser(User user);
}
