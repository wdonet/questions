import React from 'react';
import Questions from './Questions';
import fetch from '../../core/fetch';

export default {

  path: '/questions',

  async action() {
    // const response = await fetch('localhost:8080/api/questions');
    // if (response.status !== 200) throw new Error(response.statusText);
    // const content = await response.json();
    // return content && <Questions {...content._embedded} />;
    const content = {
      questions: [
        {
          id: 666,
          title: 'test',
          totalAnswers: 10,
        },
      ],
    };

    return <Questions { ...content } />;
  },

};
