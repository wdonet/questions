import React from 'react';
import api from '../../../api';
import Question from './Question';

export default {

  path: '/:id',

  async action(context, { id }) {
    const res = await api({
      method: 'GET',
      path: `/api/questions/${id}`,
    });

    const content = res.entity;
    if (! content) return undefined;

    return <Question {...content} />;
  },
};
