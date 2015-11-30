package com.nearsoft.questions.repository;

import java.util.List;
import com.nearsoft.questions.domain.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

    List<Tag> findBy_nameIn(List<String> commaDelimitedTagNameList);
}
