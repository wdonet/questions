package com.nearsoft.questions.repository;

import java.util.List;
import com.nearsoft.questions.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {

    @Query(nativeQuery = true, value =
        "SELECT * FROM question WHERE title ILIKE '%?1%' limit 10")
    List<Question> findByTitleILike(String query);

    Page<Question> findByAnswersIsNull(Pageable pageable);

    Page<Question> findByTagsId(long tagId, Pageable pageable);

    long countByAnswersIsNull();
}
