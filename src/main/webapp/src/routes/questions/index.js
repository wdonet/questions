// Child routes
import all from './all';
import order from './order';
import question from './question';

export default {

  path: '/questions',

  children: [
    all,
    order,
    question,
  ],

  async action({ next }) {
    return await next();
  },
};
