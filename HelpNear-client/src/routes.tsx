import { RouteObject } from 'react-router-dom';

// Импорты layouts

import Layout from '@layouts/Layout';

// Импорты компонентов

import Home from '@pages/Home/Home';

export const routes: RouteObject[] = [
  {
    path: '/',
    element: <Layout />,
    children: [
      {
        index: true,
        element: <Home />,
      },
    ],
  },
  {
    path: 'login',
  },
  {
    path: 'registration',
  },
];
