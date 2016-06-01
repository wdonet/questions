package com.nearsoft.questions.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nearsoft.questions.controller.form.CommentForm;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.QuestionComment;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.repository.QuestionCommentRepository;
import com.nearsoft.questions.service.QuestionService;

@Service
public class QuestionCommentServiceImpl implements com.nearsoft.questions.service.QuestionCommentService {

	@Autowired
	private QuestionCommentRepository commentRepository;
	
	@Autowired
	private QuestionService questionService;
	
	
	@Override
	public void save(final CommentForm comment, final User userDetails) {
		Question question = questionService.get(comment.getSourceId());
		QuestionComment qComment = new QuestionComment(comment, question, userDetails);
		commentRepository.save(qComment);

	}

}
