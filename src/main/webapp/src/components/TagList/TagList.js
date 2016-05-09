import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import Link from '../../components/Link';
import s from './TagList.scss';

function TagList({ tags = [], className }) {
  const tagList = tags.map(
    tag => <li key={tag} className={s.tag}><Link to="/">{tag}</Link></li>);

  return (
    <ul className={cx(s.root, className)}>
      {tagList}
    </ul>
  );
}

TagList.propTypes = {
  className: PropTypes.string,
  tags: PropTypes.array,
};

export default withStyles(s)(TagList);
