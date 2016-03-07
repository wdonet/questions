package com.nearsoft.questions.repository.search;

import com.nearsoft.questions.domain.Question;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionSearchRepository extends ElasticsearchRepository<Question, Long> {

    List<Question> findByTitleAndDescription(String query);

}
