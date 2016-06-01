package com.nearsoft.questions.repository;

import com.nearsoft.questions.domain.Tag;
import com.nearsoft.questions.domain.TagSubscription;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.service.TagsSubscriptionService;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagsSubscriptionRepository extends CrudRepository<TagSubscription, Long> {

    @Query(value = "SELECT t FROM TagSubscription s JOIN s.tag t WHERE s.user = ?1")
    List<Tag> findByUser(User user);

    TagSubscription findByUserAndTagId(User user, Long tagId);
}
