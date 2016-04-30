import React from 'react';
import Questions from './Questions';
import api from '../../api';
import capitalize from 'underscore.string/capitalize';

export default {

  path: '/questions/order/:order',

  async action(context, { order }) {
    const res = await api({
      method: 'GET',
      path: '/api/questions/',
      params: { order },
    });

    const content = res.entity._embedded;
    if (! content) return undefined;

    const title = capitalize(`${order} Questions`);

    return <Questions {...content} title={title} />;
  },
};
