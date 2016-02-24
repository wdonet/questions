package com.nearsoft.questions.repository.search;

import com.nearsoft.questions.domain.Question;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;


public interface QuestionSearchRepository extends ElasticsearchRepository<Question, Long> {

    List<Question> findByTitle(String query);

}
