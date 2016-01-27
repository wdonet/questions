import { Router } from 'express';

const router = new Router();

router.get('/', async (req, res, next) => {
  const questions = [
    {
      id: 1,
      title: 'Question 1 test',
      description: 'Description description description',
      totalAnswers: 21,
    },
    {
      id: 2,
      title: 'Question 2 test',
      description: 'Description description description',
      totalAnswers: 22,
    },
    {
      id: 3,
      title: 'Question 3 test',
      description: 'Description description description',
      totalAnswers: 23,
    },
    {
      id: 4,
      title: 'Question 4 test',
      description: 'Description description description',
      totalAnswers: 24,
    },
    {
      id: 5,
      title: 'Question 5 test',
      description: 'Description description description',
      totalAnswers: 25,
    },
  ];

  try {
    const order = req.query.order;

    if (!order || order === 'undefined') {
      res.status(200).send({ questions });
    } else {
      questions.reverse();
      res.status(200).send({ questions });
    }
  } catch (err) {
    next(err);
  }
});

export default router;
