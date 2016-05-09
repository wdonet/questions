import React, { PropTypes } from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Home.scss';
import Feature from '../../components/Feature';

const title = 'NSQuestions';

function Home(props, context) {
  context.setTitle(title);
  return (
    <section className={s.root}>
      <h1 className={s.title}>
        It's easy to find answers to <strong className={s.hl}>internal</strong>
         &amp; <strong className={s.hl}>technical</strong> questions
      </h1>
      <div className={s.features}>
        <Feature title="Search Questions"
          img={require('./busca-img.png')} imgAlt="Busca alguna pregunta"
        >
          Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod.
        </Feature>
        <Feature title="Create Questions"
          img={require('./crea-img.png')} imgAlt="Crea tu pregunta"
        >
          Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod.
        </Feature>
        <Feature title="Notify Questions"
          img={require('./notifica-img.png')} imgAlt="Notifica"
        >
          Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod.
        </Feature>
      </div>
    </section>
  );
}

Home.contextTypes = { setTitle: PropTypes.func.isRequired };

export default withStyles(s)(Home);
