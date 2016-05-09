import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import QuestionCard from '../QuestionCard';
import s from './QuestionList.scss';

function QuestionList({ questions, className }) {
  const questionList = questions.map(question =>
    <QuestionCard className={s.question} key={question.title} {...question} />);

  return (
    <section className={cx(s.root, className)}>
      {questionList}
    </section>
  );
}

QuestionList.propTypes = {
  className: PropTypes.string,
  questions: PropTypes.array,
};

export default withStyles(s)(QuestionList);
