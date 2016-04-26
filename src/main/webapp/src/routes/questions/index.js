import React from 'react';
import Questions from './Questions';
import fetch from '../../core/fetch';

export default {

  path: '/questions',

  async action() {
    const response = await fetch('/api/questions');
    if (response.status !== 200) throw new Error(response.statusText);
    const { _embedded } = await response.json();
    return content && <Questions {..._embedded} />;
  },
};
