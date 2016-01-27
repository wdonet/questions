import React, { Component, PropTypes } from 'react';
import cx from 'classnames';
import s from './Navigation.scss';
import withStyles from '../../decorators/withStyles';
import Link from '../Link';

@withStyles(s)
class Navigation extends Component {

  static propTypes = {
    className: PropTypes.string,
  };

  render() {
    return (
      <nav className={cx(s.menu, this.props.className)} role="navigation">
        <ul>
          <li>FAQ</li>
          <li>Tags</li>
          <li><Link to="/question/order/unanswered" className={s.category}>Unanswered</Link></li>
          <li><Link to="/question/order/newest" className={s.category}>Newest</Link></li>
          <li><Link to="/ask" className={s.crearPregunta}>Ask a Question Alejandro</Link></li>
        </ul>
      </nav>
    );
  }

}

export default Navigation;
