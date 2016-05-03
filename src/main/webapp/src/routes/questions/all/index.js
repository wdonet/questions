import React from 'react';
import api from '../../../api';
import QuestionsList from '../../../components/QuestionsList';
import Container from '../../../components/Container';

export default {

  path: '/',

  async action() {
    const res = await api({
      method: 'GET',
      path: '/api/questions',
    });

    const content = res.entity._embedded;
    if (! content) return undefined;

    return (
      <Container title="Questions">
        <QuestionsList {...content} />
      </Container>
    );
  },
};
