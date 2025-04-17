import Cookies from 'js-cookie';

import { API_CONFIG } from '@/api/api.config';
import { ensureError } from '@/utils/errorHandler';
import { BaseError } from '@/utils/Errors/BaseError';
import { ErrorMessage } from '@/utils/PushMessages/Error/ErrorMessages';

import { useMemo } from 'react';

import { useAppDispatch } from './reduxHooks';
import { logout } from '@/store/Slices/auth.slice';

export const useRefreshToken = () => {
  const pushErrorMessage = useMemo(() => new ErrorMessage(), []);
  const dispatch = useAppDispatch();

  const refreshToken = async () => {
    try {
      const refreshToken = Cookies.get('refreshToken');
      if (refreshToken) {
        const response = await fetch(
          `${API_CONFIG.BASE_URL}${API_CONFIG.ENDPOINTS.AUTH.REFRESH}`,
          {
            method: 'GET',
            credentials: 'include',
            headers: {
              Authorization: `Bearer ${refreshToken}`,
            },
          },
        );

        if (!response.ok)
          throw new BaseError('Error with fetch refresh token', {
            context: { endpoint: API_CONFIG.ENDPOINTS.AUTH.REFRESH },
          });

        if (response.status === 401) {
          pushErrorMessage.HTTP401();
          dispatch(logout());
          return null;
        }
      }

      pushErrorMessage.HTTP401();
      dispatch(logout());
      return null;
    } catch (err) {
      const error = ensureError(err);
      throw new BaseError('Error with fetch refresh token', {
        cause: error,
        context: { endpoint: API_CONFIG.ENDPOINTS.AUTH.REFRESH },
      });
    }
  };

  return { refreshToken };
};
