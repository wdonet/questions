import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Navigation.scss';
import Link from '../Link';

function Navigation({ className }) {
  return (
    <nav className={cx(s.root, className)} role="navigation">
      <Link className={s.category} to="/">FAQ</Link>
      <Link className={s.category} to="/">Tags</Link>
      <Link className={s.category} to="/questions/order/unanswered">Unanswered</Link>
      <Link className={s.category} to="/questions/order/newest">Newest</Link>
      <Link className={s.askAQuestion} to="/ask">Ask a Question</Link>
    </nav>
  );
}

Navigation.propTypes = {
  className: PropTypes.string,
};

export default withStyles(s)(Navigation);
