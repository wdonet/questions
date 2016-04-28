import React, { PropTypes } from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Feature.scss';

function Feature({title, img, imgSrc, children}, context) {
  return (
    <section className={s.root}>
      <img className={s.image} src={img} alt={imgSrc} />
      <h1 className={s.title}>{title}</h1>
      <p className={s.content}>{children}</p>
    </section>
  );
}

export default withStyles(s)(Feature);
