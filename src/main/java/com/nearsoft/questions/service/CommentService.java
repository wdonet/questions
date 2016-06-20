package com.nearsoft.questions.service;

import com.nearsoft.questions.controller.form.CommentForm;
import com.nearsoft.questions.domain.QuestionComment;
import com.nearsoft.questions.domain.auth.User;

public interface CommentService {

    void addToQuestion(CommentForm comment, User userDetails);

    void addToAnswer(CommentForm comment, User userDetails);
}
