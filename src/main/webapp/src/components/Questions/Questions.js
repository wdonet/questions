import React, { Component, PropTypes } from 'react';
import s from './Questions.scss';
import withStyles from '../../decorators/withStyles';
import Link from '../Link';

const title = 'Questions';

@withStyles(s)
class Questions extends Component {

  static propTypes = {
    questions: PropTypes.object.isRequired,
  };

  static contextTypes = {
    onSetTitle: PropTypes.func.isRequired,
  };

  componentWillMount() {
    this.context.onSetTitle(title);
  }

  render() {
    const questions = this.props.questions;

    return (
      <div className={s.suggestionWrapper}>
        <h1 className={s.unansweredQuestionsTitle}>
          <img src={require('./question-icon.png')} className={s.questionIcon} /> Unanswered Questions
        </h1>
        <ul className={s.suggestionsCont}>
        {questions.map(function renderQuestions(question) {
          return (
            <li>
              <Link className={s.respuestaTitulo} to={'/questions/' + question.id}>
                {question.title}
              </Link>
              <div className={s.respuestasTotal}>- {question.totalAnswers} Answers</div>
              <div className={s.tagsCont}>
                <div className={s.tagIcon}>
                  <img src={require('./tag-icon.png')} alt="tags" />
                    Tags:
                  </div>
                <span className={s.tags}>finance</span>
              </div>
            </li>
          );
        })}
        </ul>
      </div>
    );
  }

}

export default Questions;
