package com.nearsoft.questions.repository;

import com.nearsoft.questions.domain.UserNotification;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.dto.NotificationViewElement;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserNotificationRepository extends CrudRepository<UserNotification, Long> {

    List<UserNotification> findByUserOrderByIdDesc(User user);

    UserNotification findByIdAndUser(Long id, User user);

    @Transactional
    Long deleteByUserAndIdIsIn(User user, Long[] ids);

    @Modifying
    @Query("UPDATE com.nearsoft.questions.domain.UserNotification u SET u.uiNotified = true WHERE u.user = ?1")
    int markAllAsRead(User user);

    @Modifying
    @Query("UPDATE com.nearsoft.questions.domain.UserNotification u SET u.uiNotified = true WHERE u.id = ?1 AND u.user = ?2")
    int markAsRead(Long id, User user);

}
