import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './ContentWithTitle.scss';
import QuestionsList from '../../components/QuestionsList';

function Questions({ questions, title, className }, context) {
  context.setTitle(title);

  return (
    <section className={cx(s.root, className)}>
      <h1 className={s.title}>{title}</h1>
      <QuestionsList questions={questions} />
    </section>
  );
}

Questions.contextTypes = {
  setTitle: PropTypes.func.isRequired,
};

Questions.propTypes = {
  className: PropTypes.string,
  questions: PropTypes.array,
  title: PropTypes.string,
};

export default withStyles(s)(Questions);
