package com.nearsoft.questions.repository;

import com.nearsoft.questions.domain.auth.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

    @Override
    @RestResource(exported = false)
    void delete(Long id);

    @Override
    @RestResource(exported = false)
    void delete(User entity);

}
