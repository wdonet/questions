package com.nearsoft.questions.repository;

import com.nearsoft.questions.domain.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {

    List<Tag> findByNameIn(List<String> commaDelimitedTagNameList);
}
