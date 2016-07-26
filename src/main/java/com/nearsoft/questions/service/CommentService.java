package com.nearsoft.questions.service;

import com.nearsoft.questions.controller.form.CommentForm;
import com.nearsoft.questions.domain.AnswerComment;
import com.nearsoft.questions.domain.QuestionComment;
import com.nearsoft.questions.domain.auth.User;

public interface CommentService {

    QuestionComment addToQuestion(CommentForm comment, User userDetails);

    AnswerComment addToAnswer(CommentForm comment, User userDetails);
}
