// Child routes
import all from './all';
import sort from './sort';
import question from './question';

export default {

  path: '/questions',

  children: [
    all,
    sort,
    question,
  ],

  async action({ next }) {
    return await next();
  },
};
