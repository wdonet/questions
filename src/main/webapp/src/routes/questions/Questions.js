import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Questions.scss';
import QuestionCard from '../../components/QuestionCard';

const title = 'Questions';

function Questions({ questions, className }, context) {
  context.setTitle(title);

  const questionsList = questions.map(function renderQuestions(question) {
    return <QuestionCard key={question._links.self.href} {...question} />;
  });

  return (
    <section className={cx(s.root, className)}>
      <h1 className={s.title}>Questions</h1>
      <section className={s.content}>
        {questionsList}
      </section>
    </section>
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
