import React from 'react';
import api from '../../../api';
import Questions from '../../../components/Questions';

export default {

  path: '/unanswered',

  async action() {
    const res = await api({
      method: 'GET',
      path: '/api/questions',
    });

    const content = res.entity._embedded;
    if (! content) return undefined;

    return <Questions {...content} title="Unanswered Questions" />;
  },
};
