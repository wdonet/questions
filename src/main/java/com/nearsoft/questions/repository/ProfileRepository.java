package com.nearsoft.questions.repository;

import com.nearsoft.questions.domain.auth.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {


}
