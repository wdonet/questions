import { Router } from 'express';
import fetch from '../core/fetch';

const router = new Router();

router.get('/', async (req, res, next) => {
  try {
    const order = req.query.order;

    if (order !== 'undefined' && order) {
      var response = await fetch('http://localhost:8080/api/questions?order=' + order);
    } else {
      var response = await fetch('http://localhost:8080/api/questions');
    }

    res.status(200).send(await response.json());
  } catch (err) {
    next(err);
  }
});

export default router;
