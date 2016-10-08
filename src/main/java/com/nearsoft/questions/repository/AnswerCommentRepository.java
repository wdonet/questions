package com.nearsoft.questions.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.nearsoft.questions.domain.AnswerComment;

public interface AnswerCommentRepository extends CrudRepository<AnswerComment, Long> {

    void deleteByAnswerIdIn(List<Long> ids);
}
