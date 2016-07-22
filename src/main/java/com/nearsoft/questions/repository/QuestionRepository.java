package com.nearsoft.questions.repository;

import com.nearsoft.questions.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {

    @RestResource(exported = false)
    @Query(nativeQuery = true, value = "SELECT * FROM question WHERE title ILIKE '%?1%' limit 10")
    List<Question> findByTitle(String query);

    @RestResource(exported = false)
    Page<Question> findByAnswersIsNull(Pageable pageable);

    @RestResource(exported = false)
    Page<Question> findByTagsId(long tagId, Pageable pageable);

    @RestResource(exported = false)
    long countByAnswersIsNull();

    @Override
    @RestResource(exported = false)
    void delete(Long id);

    @Override
    @RestResource(exported = false)
    void delete(Question entity);
}
