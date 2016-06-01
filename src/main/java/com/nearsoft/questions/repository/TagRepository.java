package com.nearsoft.questions.repository;

import com.nearsoft.questions.domain.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {

    @RestResource(rel="findByName", path="/byName")
    List<Tag> findByNameIn(@Param("name") List<String> commaDelimitedTagNameList, Pageable pageable);

    @Override
    @RestResource(exported = false)
    void delete(Long id);

    @Override
    @RestResource(exported = false)
    void delete(Tag entity);
    
}
