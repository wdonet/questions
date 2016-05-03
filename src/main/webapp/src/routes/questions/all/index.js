import React from 'react';
import Questions from '../../../components/Questions';
import api from '../../../api';

export default {

  path: '/',

  async action() {
    const res = await api({
      method: 'GET',
      path: '/api/questions',
    });

    const content = res.entity._embedded;
    if (! content) return undefined;

    return <Questions {...content} title="Questions" />;
  },
};
