import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Search.scss';

function Search({ className }) {
  return (
    <section className={cx(s.root, className)}>
      <section className={s.container}>
        <input placeholder="What are you looking for?" className={s.searchBox} />
        <button className={s.submitSearch}>
        </button>
      </section>
    </section>
  );
}

Search.propTypes = {
  className: PropTypes.string,
};

export default withStyles(s)(Search);
