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

    Page<Question> findByTagsNameIn(List<String> tags, Pageable pageable); //todo should rename each tag to '*tag*'

    /**
     * Returns a list of {@link Question} instances that contains a title that partial match the given string
     *
     * @param title the string that will used to find the partial matches
     * @return      a list of {@link Question} instances that contains a title that partial match the given string
     */
    List<Question> findByTitleLike(String title);

}
