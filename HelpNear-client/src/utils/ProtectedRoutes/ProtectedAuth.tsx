import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { useAppSelector } from '@/hooks/reduxHooks';

type TProtectedAuth = {
  children: React.ReactNode;
};

const ProtectedAuth = ({ children }: TProtectedAuth) => {
  const location = useLocation();
  const { user } = useAppSelector((state) => state.auth);
  const isAuth = !!user;

  if (!isAuth) return <Navigate to="login" state={location.pathname} />;

  return children;
};

export default ProtectedAuth;
