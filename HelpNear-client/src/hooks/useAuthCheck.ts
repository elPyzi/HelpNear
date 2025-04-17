import Cookies from 'js-cookie';

import { API_CONFIG } from '@/api/api.config';
import { ensureError } from '@/utils/errorHandler';
import { BaseError } from '@/utils/Errors/BaseError';

import { checkAccessToken } from '@utils/checkAccessToken';
import { ErrorMessage } from '@utils/PushMessages/Error/ErrorMessages';

import { useQuery } from '@tanstack/react-query';
import { useRefreshToken } from './useRefreshToken';
import { useAppDispatch } from './reduxHooks';

import { login } from '@store/Slices/auth.slice';

export const useAuthCheck = () => {
  const pushErrorMessage = new ErrorMessage();
  const dispatch = useAppDispatch();
  const isEnableCookie: boolean =
    localStorage.getItem('COOKIE_ENABLED') === 'enable';
  const { refreshToken } = useRefreshToken();

  const checkAuth = async () => {
    if (!isEnableCookie) return null;

    try {
      const tokenCheck = await checkAccessToken({
        refreshToken,
        cb: checkAuth,
      });

      if (tokenCheck === null) return null;

      const accessToken = Cookies.get('accessToken');

      const response = await fetch(
        `${API_CONFIG.BASE_URL}${API_CONFIG.ENDPOINTS.AUTH.CHECK}`,
        {
          method: 'GET',
          credentials: 'include',
          headers: {
            'Content-type': 'application/json',
            Authorization: `Bearer ${accessToken}`,
          },
        },
      );

      if (!response.ok)
        throw new BaseError('Error with fetch refresh token', {
          context: { endpoint: API_CONFIG.ENDPOINTS.AUTH.REFRESH },
        });

      if (response.status == 401) {
        const tokenCheck = await checkAccessToken({
          refreshToken,
          cb: checkAuth,
        });

        if (tokenCheck === null) return null;
      }

      if (response.status === 403) {
        pushErrorMessage.HTTP403();
        return null;
      }

      const data = await response.json();
      dispatch(login(data));
    } catch (err) {
      const error = ensureError(err);
      throw new BaseError('Error with fetch auth check', {
        cause: error,
        context: {
          endpoint: API_CONFIG.ENDPOINTS.AUTH.CHECK,
        },
      });
    }
  };

  useQuery({
    queryKey: ['authCheck'],
    queryFn: checkAuth,
    retry: false,
    enabled: isEnableCookie,
  });
};
