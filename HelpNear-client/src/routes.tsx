import { RouteObject } from 'react-router-dom';
import { lazy } from 'react';

// Импорты layouts

import Layout from '@layouts/Layout';

// Импорты компонентов

import Home from '@pages/Home/Home';
const Account = lazy(() => import('@pages/Account/Account'));
const Professionals = lazy(() => import('@pages/Professionals/Professionals'));
const SupportCenters = lazy(() => import('@pages/Centers/Centers'));

export const routes: RouteObject[] = [
  {
    path: '/',
    element: <Layout />,
    children: [
      {
        index: true,
        element: <Home />,
      },
      {
        path: 'professionals',
        element: <Professionals />,
      },
      {
        path: 'support-centers',
        element: <SupportCenters />,
      },
      {
        path: 'account',
        element: <Account />,
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
