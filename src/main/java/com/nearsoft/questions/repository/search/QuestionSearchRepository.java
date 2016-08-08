package com.nearsoft.questions.repository.search;

import java.util.List;
import com.nearsoft.questions.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionSearchRepository extends ElasticsearchRepository<Question, Long> {

    Page<Question> findByTitleOrDescription(String title, String description, Pageable pageable);

    Page<Question> findByTagsNameIn(List<String> tags, Pageable pageable); //todo should rename each tag to '*tag*'

//    @Query("{\"bool\" : {\"should\" : [" +
//        " {\"field\" : {\"title\" : \"?0\"}}" +
//        " {\"field\" : {\"description\" : \"?0\"}}," +
//        " {\"field\" : {\"answers.description\" : \"?0\"}}" +
//        " ]}}")
//    Page<Question> findSmt(String qry, Pageable pageable);

}
