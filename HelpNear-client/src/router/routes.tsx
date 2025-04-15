import { RouteObject } from 'react-router-dom';
import { lazy } from 'react';

import { PAGE_CONFIG } from '@/config/page.config';

// Импорты layouts

import Layout from '@layouts/Layout';

// Импорты компонентов

import Home from '@pages/Home/Home';
import ProtectedAuth from '@/router/ProtectedRoutes/ProtectedAuth';
const Account = lazy(() => import('@pages/Account/Account'));
const Professionals = lazy(() => import('@pages/Professionals/Professionals'));
const SupportCenters = lazy(() => import('@pages/Centers/Centers'));
const Login = lazy(() => import('@pages/Auth/Login/Login'));
const Registration = lazy(
  () => import('@pages/Auth/Registration/Registration'),
);

export const routes: RouteObject[] = [
  {
    path: PAGE_CONFIG.HOME,
    element: <Layout />,
    children: [
      {
        index: true,
        element: <Home />,
      },
      {
        path: PAGE_CONFIG.PROFESSIONAL,
        element: <Professionals />,
      },
      {
        path: PAGE_CONFIG.SUPPORT_CENTER,
        element: <SupportCenters />,
      },
    ],
  },
  {
    path: PAGE_CONFIG.ACCOUNT,
    element: (
      <ProtectedAuth>
        <Account />
      </ProtectedAuth>
    ),
    children: [
      { index: true, path: PAGE_CONFIG.PROFILE },
      { path: PAGE_CONFIG.CONCLUSION },
      {
        path: PAGE_CONFIG.PROBLEMS,
        children: [
          { index: true },
          { path: PAGE_CONFIG.HISTORY_PROBLEMS },
          { path: PAGE_CONFIG.PROCESSING_PROBLEMS },
        ],
      },
      { path: PAGE_CONFIG.USERS },
    ],
  },
  {
    path: PAGE_CONFIG.LOGIN,
    element: <Login />,
  },
  {
    path: PAGE_CONFIG.REGISTRATION,
    element: <Registration />,
  },
  { path: PAGE_CONFIG.ERROR },
];
