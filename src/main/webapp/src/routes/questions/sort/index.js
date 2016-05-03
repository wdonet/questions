import React from 'react';
import api from '../../../api';
import capitalize from 'underscore.string/capitalize';
import Questions from '../Questions';

export default {

  path: '/sort/:sort',

  async action(context, { sort }) {
    const res = await api({
      method: 'GET',
      path: '/api/questions',
      params: { order: sort },
    });

    const content = res.entity._embedded;
    if (! content) return undefined;

    const title = capitalize(`${sort} Questions`);

    return <Questions {...content} title={title} />;
  },
};
