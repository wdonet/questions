import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import Link from '../../components/Link';
import s from './QuestionCard.scss';

function QuestionCard(props) {
  const { title, totalAnswers, tags = [], user, _links, className } = props;

  const id = _links.self.href.split('/').pop();
  const url = `/questions/${id}`;

  const tagsList = tags.map(
    tag => <li key={tag} className={s.tag}><Link to="/">{tag}</Link></li>);

  return (
    <article className={cx(s.root, className)}>
      <header>
        <h1 className={s.title}><Link to={url}>{title}</Link></h1>
        <span className={s.totalAnswers}>- {totalAnswers} Answers</span>
      </header>

      <footer>
        <strong className={s.tagsHeader}>Tags:</strong>
        <ul className={s.tagsList}>
          {tagsList}
        </ul>
        <b className={s.author}><small>Asked by</small> {user.fullName}</b>
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
  user: PropTypes.object,
};

export default withStyles(s)(QuestionCard);
