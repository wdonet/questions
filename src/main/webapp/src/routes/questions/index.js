// Child routes
import all from './all';
import question from './question';
import newest from './newest';
import unanswered from './unanswered';

export default {

  path: '/questions',

  children: [
    all,
    newest,
    unanswered,
    question,
  ],

  async action({ next }) {
    return await next();
  },
};
