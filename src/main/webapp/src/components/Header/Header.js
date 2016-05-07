import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Header.scss';
import Navigation from '../Navigation';
import Link from '../Link';

function Header({ className }) {
  return (
    <header className={cx('header', className)}>
      <div className={s.container}>
        <Link to="/">
          <img className={s.logo}
            src={require('./logo.png')} alt="Nearsoft Questions"
          />
        </Link>
        <Navigation />
      </div>
    </header>
  );
}

Header.propTypes = {
  className: PropTypes.string,
};

export default withStyles(s)(Header);
