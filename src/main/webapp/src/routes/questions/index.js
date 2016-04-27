import React from 'react';
import Questions from './Questions';
import fetch from '../../core/fetch';
import api from '../../api';

export default {

  path: '/questions/order/:order',

  async action({ params }) {

    const res = await api({
      method: 'GET',
      path: '/api/questions/',
      params: {order: params.order},
    });

    const content = res.entity._embedded;
    if (! content) return undefined

    return <Questions {...content} />;
  },
};
