package com.nearsoft.questions.repository;

import java.util.List;
import com.nearsoft.questions.domain.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {

    @Query(value = "SELECT * FROM question WHERE _title ILIKE '%?1%' limit 10", nativeQuery = true)
    List<Question> findByTitleILike(String query);
}
