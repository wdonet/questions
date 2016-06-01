package com.nearsoft.questions.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nearsoft.questions.controller.form.CommentForm;
import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.AnswerComment;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.repository.AnswerCommentRepository;
import com.nearsoft.questions.service.AnswerCommentService;
import com.nearsoft.questions.service.AnswerService;

@Service
public class AnswerCommentServiceImpl implements AnswerCommentService {

	@Autowired
	private AnswerCommentRepository commentRepository;
	
	@Autowired
	private AnswerService answerService;
	
	@Override
	public void save(CommentForm comment, User userDetails) {
		Answer answer = answerService.get(comment.getSourceId());
		AnswerComment qComment = new AnswerComment(comment, answer, userDetails);
		commentRepository.save(qComment);
	}

}
