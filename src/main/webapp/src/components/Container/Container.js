import React, { PropTypes } from 'react';
import cx from 'classnames';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './Container.scss';

function Container({ title, children, className }, context) {
  context.setTitle(title);

  const maybeTitle = !!title && <h1 className={s.title}>{title}</h1>;

  return (
    <section className={cx(s.root, className)}>
      {maybeTitle}

      <section className={s.content}>
        {children}
      </section>
    </section>
  );
}

Container.contextTypes = {
  setTitle: PropTypes.func.isRequired,
};

Container.propTypes = {
  children: PropTypes.element.isRequired,
  className: PropTypes.string,
  title: PropTypes.string,
};

export default withStyles(s)(Container);
