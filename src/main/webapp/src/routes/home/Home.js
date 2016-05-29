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
        Search And Find Questions
      </h1>
      <div className={s.search}>
        <input placeholder="What are you looking for?" className={s.searchBox} />
        <button className={s.submitSearch}>
        </button>
        <p className={s.quickTip}>
          Quick Tip On How to Search: ( Ex.- Cómo hacer una declaración de impuestos? ).
        </p>
      </div>
      <div className={s.features}>
        <h2 className={s.subtitle}>
          How it works
        </h2>
        <section className={s.featuresContainer}>
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
        </section>
      </div>
    </section>
  );
}

Home.contextTypes = { setTitle: PropTypes.func.isRequired };

export default withStyles(s)(Home);
