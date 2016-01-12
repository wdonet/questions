package com.nearsoft.questions.repository;

import com.nearsoft.questions.domain.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

    List<Tag> findByNameIn(List<String> commaDelimitedTagNameList);
}
