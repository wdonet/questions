package com.nearsoft.questions.service.impl;

import com.nearsoft.questions.domain.Tag;
import com.nearsoft.questions.domain.TagSubscription;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.dto.UserTag;
import com.nearsoft.questions.error.TagNotFoundException;
import com.nearsoft.questions.error.UserNotSubscribedToTagException;
import com.nearsoft.questions.repository.TagRepository;
import com.nearsoft.questions.repository.TagsSubscriptionRepository;
import com.nearsoft.questions.service.TagsSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class TagsSubscriptionServiceImpl implements TagsSubscriptionService {

    @Autowired
    private TagsSubscriptionRepository tagsSubscriptionRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public void subscribe(User user, Long tagId) throws TagNotFoundException {
        Tag tag = getTag(tagId);

        if(!isSubscribed(user, tagId)) {
            TagSubscription tagSubscription = new TagSubscription();

            tagSubscription.setTag(tag);
            tagSubscription.setUser(user);

            tagsSubscriptionRepository.save(tagSubscription);
        }
    }

    @Override
    public void unsubscribe(User user, Long tagId) throws UserNotSubscribedToTagException {
        TagSubscription tagSubscription = tagsSubscriptionRepository.findByUserAndTagId(user, tagId);

        if (tagSubscription == null) {
            throw new UserNotSubscribedToTagException(user, tagId);
        }

        tagsSubscriptionRepository.delete(tagSubscription);
    }

    @Override
    public boolean isSubscribed(User user, Long tagId) {
        TagSubscription tagSubscription = tagsSubscriptionRepository.findByUserAndTagId(user, tagId);

        return tagSubscription != null;
    }



    @Override
    public List<Tag> findTagsByUser(User user) {
        List<Tag> ownedTags = tagsSubscriptionRepository.findByUser(user);

        return ownedTags;
    }

    @Override
    public List<UserTag> getAllTagsWithSubscriptionFlagSortedByName(User user) {
        Set<Tag> ownedTags = new HashSet<>();
        List<UserTag> allUserTags = new LinkedList<>();

        ownedTags.addAll(tagsSubscriptionRepository.findByUser(user));

        Sort sort = new Sort(Sort.Direction.ASC, "name");
        List<Tag> allTagsRow = (List<Tag>) tagRepository.findAll(sort);

        for(Tag tag : allTagsRow){
            UserTag userTag = new UserTag();

            userTag.setTag(tag);
            userTag.setSubscribed(ownedTags.contains(tag));

            allUserTags.add(userTag);
        }

        return allUserTags;
    }

    @Override
    public List<User> findByTagsIsIn(List<Tag> tags) {

        return tagsSubscriptionRepository.findByTagsIsIn(tags);
    }


    private Tag getTag(Long tagId) throws TagNotFoundException {
        Tag tag = tagRepository.findOne(tagId);

        if (tag == null) {
            throw new TagNotFoundException(tagId);
        }

        return tag;
    }
}
