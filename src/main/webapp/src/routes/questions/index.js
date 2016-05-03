// Child routes
import all from './all';
import question from './question';
import sort from './sort';
import unanswered from './unanswered';

export default {

  path: '/questions',

  children: [
    all,
    sort,
    unanswered,
    question,
  ],

  async action({ next }) {
    return await next();
  },
};
