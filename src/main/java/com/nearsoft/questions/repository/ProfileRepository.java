package com.nearsoft.questions.repository;

import com.nearsoft.questions.domain.auth.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
@RestResource(exported = false)
public interface ProfileRepository extends CrudRepository<Profile, Long> {


}
