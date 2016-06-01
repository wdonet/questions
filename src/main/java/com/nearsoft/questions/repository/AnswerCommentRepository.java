package com.nearsoft.questions.repository;

import org.springframework.data.repository.CrudRepository;

import com.nearsoft.questions.domain.AnswerComment;

public interface AnswerCommentRepository extends CrudRepository<AnswerComment, Long> {

}
