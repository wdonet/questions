package com.nearsoft.questions.repository.search;

import com.nearsoft.questions.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionSearchRepository extends ElasticsearchRepository<Question, Long> {

    Page<Question> findByTitleOrDescription(String title, String description, Pageable pageable);

    List<Question> findByDescriptionLike(String description);


}
