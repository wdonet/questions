package com.nearsoft.questions.repository;

import com.nearsoft.questions.domain.auth.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @RestResource(rel = "findByEmail", path = "/byEmail")
    User findByEmail(@Param("q") String email);

    @Override
    @RestResource(exported = false)
    void delete(Long id);

    @Override
    @RestResource(exported = false)
    void delete(User entity);


    // NEEDS to be used every time we recover a User from the database
    @Query(nativeQuery = true, value = "SELECT SUM(total_points.points) \n" +
        "FROM ( \n" +
        "SELECT SUM(ra.points) AS points \n" +
        "FROM rule_answer_transaction ra \n" +
        "LEFT JOIN answer a ON ra.answer_id = a.id \n" +
        "WHERE a.user_id = ?1\n" +
        "\n" +
        "UNION ALL \n" +
        "\n" +
        "SELECT SUM(rq.points) AS points \n" +
        "FROM rule_question_transaction rq \n" +
        "LEFT JOIN question q ON rq.question_id = q.id \n" +
        "WHERE q.user_id = ?1 \n" +
        ") total_points ")
    Integer getPointsForUserId(Long userId);
}
