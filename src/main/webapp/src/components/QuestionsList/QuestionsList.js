import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import QuestionCard from '../QuestionCard';
import s from './QuestionsList.scss';

function QuestionsList({ questions, className }) {
  const questionsList = questions.map(
    question => <QuestionCard key={question._links.self.href} {...question} />);

  return (
    <section className={cx(s.root, className)}>
      {questionsList}
    </section>
  );
}

QuestionsList.propTypes = {
  className: PropTypes.string,
  questions: PropTypes.array,
};

export default withStyles(s)(QuestionsList);
