import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import Link from '../../components/Link';
import s from './TagList.scss';

function TagList({ tags = [], className }) {
  const tagList = tags.map(
    tag => <span key={tag} className={s.tag}> <Link to="/">{tag}</Link></span>);

  return (
    <section className={cx(s.root, className)}>
      <span className={s.label}>Categories:</span>
      {tagList}
    </section>
  );
}

TagList.propTypes = {
  className: PropTypes.string,
  tags: PropTypes.array,
};

export default withStyles(s)(TagList);
