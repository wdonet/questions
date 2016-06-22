package com.nearsoft.questions.service.impl;

import com.nearsoft.questions.domain.Answer;
import com.nearsoft.questions.domain.AnswerComment;
import com.nearsoft.questions.repository.AnswerCommentRepository;
import com.nearsoft.questions.service.AnswerService;
import com.nearsoft.questions.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nearsoft.questions.controller.form.CommentForm;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.domain.QuestionComment;
import com.nearsoft.questions.domain.auth.User;
import com.nearsoft.questions.repository.QuestionCommentRepository;
import com.nearsoft.questions.service.QuestionService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private QuestionCommentRepository questionCommentRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerCommentRepository answerCommentRepository;

    @Autowired
    private AnswerService answerService;

    @Override
    public void addToQuestion(final CommentForm form, final User userDetails) {
        Question question = questionService.get(form.getSourceId());
        QuestionComment comment = new QuestionComment(form, question, userDetails);
        questionCommentRepository.save(comment);
    }

    @Override
    public void addToAnswer(CommentForm form, User userDetails) {
        Answer answer = answerService.get(form.getSourceId());
        AnswerComment comment = new AnswerComment(form, answer, userDetails);
        answerCommentRepository.save(comment);
    }
}
