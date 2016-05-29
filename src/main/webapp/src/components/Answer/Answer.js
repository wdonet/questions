import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Answer.scss';

function Answer({ description, user, className }) {
  return (
    <article className={cx(s.root, className)}>
      <footer className={s.footer}>
        Answered By <span className={s.author}>{user.fullName}</span>
      </footer>
      <section className={s.description}>
        {description}
      </section>
    </article>
  );
}

Answer.propTypes = {
  className: PropTypes.string,
  description: PropTypes.string,
  user: PropTypes.object,
};

export default withStyles(s)(Answer);
