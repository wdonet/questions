import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Navigation.scss';
import Link from '../Link';

function Navigation({ className }) {
  return (
    <nav className={cx(s.root, className)} role="navigation">
      <Link className={s.link} to="/">Search</Link>
      <Link className={s.link} to="/">Tags</Link>
      <Link className={s.link} to="/questions/unanswered">Unanswered</Link>
      <Link className={s.link} to="/questions/sort/newest">Newest</Link>
      <Link className={s.linkAsk} to="/ask">Ask a Question</Link>
    </nav>
  );
}

Navigation.propTypes = {
  className: PropTypes.string,
};

export default withStyles(s)(Navigation);
