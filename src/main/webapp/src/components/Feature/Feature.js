import React, { PropTypes } from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Feature.scss';

function Feature({ title, img, imgAlt, children }) {
  return (
    <section className={s.root}>
      <img className={s.image} src={img} alt={imgAlt} />
      <h1 className={s.title}>{title}</h1>
      <p className={s.content}>
        {children}
      </p>
    </section>
  );
}

Feature.propTypes = {
  title: PropTypes.string.isRequired,
  img: PropTypes.string.isRequired,
  imgAlt: PropTypes.string.isRequired,
  children: PropTypes.string.isRequired,
};

export default withStyles(s)(Feature);
