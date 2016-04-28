import React from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Header.scss';
import Navigation from '../Navigation';
import Link from '../Link';

function Header({ className }) {
  return (
    <header className={cx('header', className)}>
      <div className={s.container}>
        <Link to="/"><img src={require('./logo.png')} className={s.logo} /></Link>
        {/*
        <form name="go_signin" className={s.go_signin} action="/auth/google" method="post">
          <input className={s.submit} type="submit" defaultValue="Sign in with your Nearsoft account" />
          <input type="hidden" name="scope" defaultValue="profile email" />
        </form>
        */}
        <Navigation />
      </div>
    </header>
  );
}

export default withStyles(s)(Header);
