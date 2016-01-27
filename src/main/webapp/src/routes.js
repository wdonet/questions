import React from 'react';
import Router from 'react-routing/src/Router';
import fetch from './core/fetch';
import App from './components/App';
import ContentPage from './components/ContentPage';
import Questions from './components/Questions';
import LoginPage from './components/LoginPage';
import HomePage from './components/HomePage';
import NotFoundPage from './components/NotFoundPage';
import ErrorPage from './components/ErrorPage';

const router = new Router(on => {
  on('*', async (state, next) => {
    const component = await next();
    return component && <App context={state.context}>{component}</App>;
  });

  on('/', async () => <HomePage />);

  on('/questions', async () => {
    const response = await fetch(`/api/questions`);
    const content = await response.json();
    return content && <Questions {...content} />;
  });

  on('/questions/:order', async (state) => {
    const response = await fetch(`/api/questions?order=${state.params.order}`);
    const content = await response.json();
    return content && <Questions {...content} />;
  });

  on('/login', async () => <LoginPage />);

  on('*', async (state) => {
    const response = await fetch(`/api/content?path=${state.path}`);
    const content = await response.json();
    return content && <ContentPage {...content} />;
  });

  on('error', (state, error) => state.statusCode === 404 ?
    <App context={state.context} error={error}><NotFoundPage /></App> :
    <App context={state.context} error={error}><ErrorPage /></App>
  );
});

export default router;
