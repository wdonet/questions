import React, { Component } from 'react';
import s from './Header.scss';
import withStyles from '../../decorators/withStyles';
import Navigation from '../Navigation';

@withStyles(s)
class Header extends Component {

  render() {
    return (
      <header>
        <div className={s.headerWrapper}>
          <img src={require('./logo.png')} className={s.logo} />
          {/*
          <form name="go_signin" id={s.go_signin} action="/auth/google" method="post"><input type="submit" defaultValue="Sign in with your Nearsoft account" /><input type="hidden" name="scope" defaultValue="profile email" /></form>
          */}
          <Navigation />
        </div>
      </header>
    );
  }

}

export default Header;
