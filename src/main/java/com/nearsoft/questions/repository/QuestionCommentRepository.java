package com.nearsoft.questions.repository;

import org.springframework.data.repository.CrudRepository;

import com.nearsoft.questions.domain.QuestionComment;

public interface QuestionCommentRepository extends CrudRepository<QuestionComment, Long> {

    void deleteByQuestionId(Long questionId);
}
