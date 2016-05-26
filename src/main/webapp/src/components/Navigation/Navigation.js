import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Navigation.scss';
import Link from '../Link';

function Navigation({ className }) {
  return (
    <nav className={cx(s.root, className)} role="navigation">
      <Link className={s.link} to="/categories">Categories</Link>
      <Link className={s.link} to="/questions/unanswered">Unanswered</Link>
      <Link className={s.link} to="/questions/sort/newest">Newest</Link>
      <Link className={cx(s.link, s.login)} to="/login">
        Sign in
      </Link>
    </nav>
  );
}

Navigation.propTypes = {
  className: PropTypes.string,
};

export default withStyles(s)(Navigation);
