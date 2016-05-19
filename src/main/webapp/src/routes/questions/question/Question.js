import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Question.scss';
import TagList from '../../../components/TagList';
import Answer from '../../../components/Answer';

function Question(props, context) {
  const { title, tags, description, user, answers, className } = props;

  context.setTitle(title);

  const answerList = answers.length ? answers.map(answer =>
    <Answer className={s.answer} {...answer} />) : 'No answers';

  return (
    <article className={cx(s.root, className)}>
      <header>
        <h1 className={s.title}>{title}</h1>
        {!!tags.length && <TagList tags={tags} />}
      </header>

      <section className={s.description}>
        {description}
      </section>

      <footer className={s.footer}>
        <b className={s.author}>asked by owner.fullName</b>
      </footer>

      <section className={s.answers}>
        <h3>What people have answered</h3>
        {answerList}

        <section className={s.addAnswer}>
          <h3>Add an answer</h3>
          <textarea className={s.answerInput}
            placeholder="Add a detailed answer"
          />
          <button className={cx(s.addButton, 'blue')}>Add</button>
        </section>
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
  answers: PropTypes.array,
  title: PropTypes.string,
  totalAnswers: PropTypes.number,
  user: PropTypes.object,
};

Question.defaultProps = {
  answers: [],
  tags: [],
};

export default withStyles(s)(Question);
