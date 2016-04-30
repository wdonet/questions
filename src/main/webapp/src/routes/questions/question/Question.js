import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Question.scss';

function Question(props, context) {
  context.setTitle(props.title);

  return (
    <section className={cx(s.root, props.className)}>
      {props.title}
    </section>
  );
}

Question.contextTypes = {
  setTitle: PropTypes.func.isRequired,
};

Question.propTypes = {
  className: PropTypes.string,
  description: PropTypes.string,
  title: PropTypes.string,
  totalAnswers: PropTypes.number,
  user: PropTypes.object,
};

export default withStyles(s)(Question);
