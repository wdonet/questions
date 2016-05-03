import React from 'react';
import api from '../../../api';
import Question from './Question';
import Container from '../../../components/Container';

export default {

  path: '/:id',

  async action(context, { id }) {
    const res = await api({
      method: 'GET',
      path: `/api/questions/${id}`,
    });

    const content = res.entity;
    if (! content) return undefined;

    return (
      <Container>
        <Question {...content} />
      </Container>
    );
  },
};
