package com.nearsoft.questions.service;

import com.nearsoft.questions.controller.form.CommentForm;
import com.nearsoft.questions.domain.QuestionComment;
import com.nearsoft.questions.domain.auth.User;

public interface QuestionCommentService {

	void save(CommentForm comment, User userDetails);
}
