package com.nearsoft.questions.repository;

import com.nearsoft.questions.domain.Notification;
import com.nearsoft.questions.domain.auth.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

    List<Notification> findByUserOrderByIdDesc(User user);

    @Transactional
    Long deleteByUserAndIdIsIn(User user, Long[] ids);

}
