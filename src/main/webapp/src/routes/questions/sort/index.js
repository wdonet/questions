import React from 'react';
import api from '../../../api';
import capitalize from 'underscore.string/capitalize';
import QuestionList from '../../../components/QuestionList';
import Container from '../../../components/Container';

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

    return (
      <Container title={title}>
        <QuestionList {...content} />
      </Container>
    );
  },
};
