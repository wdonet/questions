package com.nearsoft.questions.service;

import com.nearsoft.questions.domain.Tag;
import com.nearsoft.questions.domain.TagSubscription;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.dto.UserTag;
import com.nearsoft.questions.error.TagNotFoundException;
import com.nearsoft.questions.error.UserNotSubscribedToTagException;

import java.util.List;
import java.util.stream.Stream;

public interface TagsSubscriptionService {
    void subscribe(User user, Long tagId) throws TagNotFoundException;

    void unsubscribe(User user, Long tagId) throws UserNotSubscribedToTagException;

    boolean isSubscribed(User user, Long tagId);

    List<Tag> findTagsByUser(User user);

    List<UserTag> getAllTagsWithSubscriptionFlagSortedByName(User user);

    List<User> findByTagsIsIn(List<Tag> tags);

    Stream<TagSubscription> findByTagIsInOrderByUserAsc(List<Tag> tags);
}
