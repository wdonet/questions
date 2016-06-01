package com.nearsoft.questions.repository;

import com.nearsoft.questions.domain.Tag;
import com.nearsoft.questions.domain.TagSubscription;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.service.TagsSubscriptionService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagsSubscriptionRepository extends JpaRepository<TagSubscription, Long> {

    @Query(value = "SELECT t FROM TagSubscription s JOIN s.tag t WHERE s.user = ?1")
    List<Tag> findByUser(User user);

    TagSubscription findByUserAndTagId(User user, Long tagId);

    @Query(value = "SELECT u FROM TagSubscription s JOIN s.tag t JOIN s.user u WHERE s.tag IN ?1")
    List<User> findByTagsIsIn(List<Tag> tags);

    //List<TagSubscription> findByTags(List<Tag> tags);
}
