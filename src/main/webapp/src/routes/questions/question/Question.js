import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Question.scss';
import TagList from '../../../components/TagList';

function Question(props, context) {
  const { title, tags, description, user, className } = props;

  context.setTitle(title);

  return (
    <article className={cx(s.root, className)}>
      <header>
        <h1 className={s.title}>{title}</h1>
        <TagList tags={tags} />
      </header>

      <section className={s.description}>
        {description}
      </section>

      <footer className={s.footer}>
        asked by {user.fullName}
      </footer>

      <section className={s.answers}>
        <h2>What people have answered</h2>
      </section>
    </article>
  );
}

Question.contextTypes = {
  setTitle: PropTypes.func.isRequired,
};

Question.propTypes = {
  className: PropTypes.string,
  description: PropTypes.string,
  tags: PropTypes.array,
  title: PropTypes.string,
  totalAnswers: PropTypes.number,
  user: PropTypes.object,
};

export default withStyles(s)(Question);
