import React, { Component, PropTypes } from 'react';
import s from './HomePage.scss';
import withStyles from '../../decorators/withStyles';

const title = 'NSQuestions';

@withStyles(s)
class HomePage extends Component {

  static contextTypes = {
    onSetTitle: PropTypes.func.isRequired,
  };

  componentWillMount() {
    this.context.onSetTitle(title);
  }

  render() {
    return (
      <div className={s.suggestionWrapper}>
        <h1 className={s.title}>
          It's easy to find answers to <span className={s.highlight}>internal</span>  &amp; <span className={s.highlight}>technical</span> questions
        </h1>
        <section className={s.howItWorks}>
          <div className={s.howCont}>
            <img src={require('./busca-img.png')} alt="Busca alguna pregunta" />
            <h1>Search Questions</h1>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod.</p>
          </div>
          <div className={s.howCont}>
            <img src={require('./crea-img.png')} alt="Crea tu pregunta" />
            <h1>Create Questions</h1>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod.</p>
          </div>
            <div className={s.howCont}>
            <img src={require('./notifica-img.png')} alt="Notifica" />
            <h1>Notify Questions</h1>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod.</p>
          </div>
        </section>
      </div>
    );
  }

}

export default HomePage;
