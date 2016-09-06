package com.nearsoft.questions.repository;

import com.nearsoft.questions.domain.Tag;
import com.nearsoft.questions.domain.TagSubscription;
import com.nearsoft.questions.domain.auth.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.stream.Stream;

public interface TagsSubscriptionRepository extends JpaRepository<TagSubscription, Long> {

    @Query(value = "SELECT t FROM TagSubscription s JOIN s.tag t WHERE s.user = ?1")
    List<Tag> findByUser(User user);

    TagSubscription findByUserAndTagId(User user, Long tagId);

    @Query(value = "SELECT u FROM TagSubscription s JOIN s.tag t JOIN s.user u WHERE s.tag IN ?1")
    List<User> findByTagsIsIn(List<Tag> tags);

    Stream<TagSubscription> findByTagIsInOrderByTagAsc(List<Tag> tags);
}
