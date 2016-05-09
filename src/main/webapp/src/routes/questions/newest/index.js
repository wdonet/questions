import React from 'react';
import api from '../../../api';
import capitalize from 'underscore.string/capitalize';
import QuestionList from '../../../components/QuestionList';
import Container from '../../../components/Container';

export default {

  path: '/sort/newest',

  async action() {
    const res = await api({
      method: 'GET',
      path: '/api/questions',
      params: { sort: 'createdAt' },
    });

    const content = res.entity._embedded;
    if (!content) return undefined;

    const title = capitalize('Newest Questions');

    return (
      <Container title={title}>
        <QuestionList {...content} />
      </Container>
    );
  },
};
