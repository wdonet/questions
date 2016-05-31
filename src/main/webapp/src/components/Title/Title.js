import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Title.scss';

function Title({ children, className }) {
  return (
    <h1 className={cx(s.root, className)}>
      {children}
    </h1>
  );
}

Title.propTypes = {
  children: PropTypes.string.isRequired,
  className: PropTypes.string,
};

export default withStyles(s)(Title);
