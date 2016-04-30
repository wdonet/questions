import React from 'react';
import api from '../../../api';
import capitalize from 'underscore.string/capitalize';
import Questions from '../Questions';

export default {

  path: '/order/:order',

  async action(context, { order }) {
    const res = await api({
      method: 'GET',
      path: '/api/questions',
      params: { order },
    });

    const content = res.entity._embedded;
    if (! content) return undefined;

    console.log('test order route');
    const title = capitalize(`${order} Questions`);

    return <Questions {...content} title={title} />;
  },
};
