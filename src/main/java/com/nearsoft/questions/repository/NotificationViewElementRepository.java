package com.nearsoft.questions.repository;

import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.domain.dto.NotificationViewElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.EntityManager;

@Repository
public class NotificationViewElementRepository {


    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<NotificationViewElement> getNotificationsForView(User user) {
        String queryString = "SELECT new com.nearsoft.questions.domain.dto.NotificationViewElement(n, un)" +
                " FROM com.nearsoft.questions.domain.UserNotification un " +
                " JOIN un.notification n WHERE un.user = :user" +
                " ORDER BY un.id DESC";

        javax.persistence.Query query = entityManager.createQuery(queryString);
        query.setParameter("user", user);

        List<NotificationViewElement> notifications = query.getResultList();

        return notifications;
    }
}
