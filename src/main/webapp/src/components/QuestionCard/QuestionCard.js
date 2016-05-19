import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import Link from '../../components/Link';
import TagList from '../../components/TagList';
import s from './QuestionCard.scss';

function QuestionCard(props) {
  const { id, title, totalAnswers, tags, user, _links, className } = props;

  const url = `/questions/${id}`;

  return (
    <article className={cx(s.root, className)}>
      <header>
        <h1 className={s.title}><Link to={url}>{title}</Link></h1>
        <span className={s.totalAnswers}>- {totalAnswers} Answers</span>
      </header>

      <footer>
        <strong className={s.tagsHeader}>Tags:</strong>
        <TagList tags={tags} />
        <b className={s.author}>
          <small>Asked by</small>
          owner.fullName
        </b>
      </footer>
    </article>
  );
}

QuestionCard.propTypes = {
  _links: PropTypes.object,
  className: PropTypes.string,
  tags: PropTypes.array,
  title: PropTypes.string,
  totalAnswers: PropTypes.number,
  id: PropTypes.number,
  // owner: PropTypes.object,
};

QuestionCard.defaultProps = {
  tags: [],
};

export default withStyles(s)(QuestionCard);
