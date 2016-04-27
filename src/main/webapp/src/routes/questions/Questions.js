import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import Link from '../../components/Link';
import s from './Questions.scss';

const title = 'Questions';

function Questions({ questions, className }, context) {
  context.setTitle(title);
  return (
    <div className={cx(s.container, className)}>
      <h1 className={s.unansweredQuestionsTitle}>
        <img src={require('./question-icon.png')} className={s.questionIcon} />
        Questions
      </h1>
      <ul className={s.suggestionsCont}>
        {questions.map(function renderQuestions(question) {
          return (
              <li key={question._links.self.href}>
              <Link className={s.answerTitle} to={`/questions/${question.id}`}>
                {question.title}
              </Link>
              <div className={s.answersTotal}>- {question.totalAnswers} Answers</div>
              <div className={s.tagsCont}>
                <div className={s.tagIcon}>
                  <img src={require('./tag-icon.png')} alt="tags" />
                  Tags:
                </div>
                <span className={s.tags}>finance</span>
              </div>
            </li>
          );
        })}
      </ul>
    </div>
  );
}

Questions.contextTypes = {
  setTitle: PropTypes.func.isRequired,
};

Questions.propTypes = {
  className: PropTypes.string,
  questions: PropTypes.array,
};

export default withStyles(s)(Questions);
