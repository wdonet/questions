import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Question.scss';
import Answer from '../../../components/Answer';
import Link from '../../../components/Link';
import Search from '../../../components/Search';
import TagList from '../../../components/TagList';

function Question(props, context) {
  const { title, tags, description, authorName, answers, className } = props;

  context.setTitle(title);

  const answerList = answers.length ? answers.map(answer =>
    <Answer className={s.answer} {...answer} />) : 'No answers';

  return (
    <article className={cx(s.root, className)}>
      <Search className={s.search} />
      <header>
        <Link className={s.backLink} to="/">« BACK</Link>
        <h1 className={s.title}>{title}</h1>
        <section className={s.metadata}>
          {!!tags.length && <TagList tags={tags} />}
          <span className={s.author}>Asked By {authorName}</span>
        </section>
      </header>

      <section className={s.description}>
        {description}
      </section>

      <footer className={s.footer}>
      </footer>

      <section className={s.answers}>
        <h2 className={s.answersTitle}>Answers</h2>
        {answerList}

        <section className={s.addAnswer}>
          <h3 className={s.addAnswerTitle}>Add an answer</h3>
          <textarea className={s.answerInput}
            placeholder="Add a detailed answer"
          />
          <button className={cx(s.addButton, 'orange')}>Add</button>
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
  authorName: PropTypes.string,
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
