import React, { PropTypes } from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Home.scss';

const title = 'NSQuestions';

function Home(props, context) {
  context.setTitle(title);
  return (
    <div className={s.container}>
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

Home.contextTypes = { setTitle: PropTypes.func.isRequired };

export default withStyles(s)(Home);
