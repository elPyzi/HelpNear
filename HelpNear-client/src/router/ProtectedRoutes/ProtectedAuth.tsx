import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { useAppSelector } from '@/hooks/reduxHooks';

import { PAGE_CONFIG } from '@/config/page.config';

type TProtectedAuth = {
  children: React.ReactNode;
};

const ProtectedAuth = ({ children }: TProtectedAuth) => {
  const location = useLocation();
  const { user } = useAppSelector((state) => state.auth);
  const isAuth = !!user;

  if (!isAuth) {
    return (
      <Navigate
        to={`/${PAGE_CONFIG.AUTH}/${PAGE_CONFIG.LOGIN}`}
        state={{ from: location.pathname }}
        replace
      />
    );
  }

  return children;
};

export default ProtectedAuth;
