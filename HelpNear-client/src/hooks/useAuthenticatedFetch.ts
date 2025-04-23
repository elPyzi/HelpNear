import Cookies from 'js-cookie';

import { useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRefreshToken } from './useRefreshToken';
import { ErrorMessage } from '../utils/PushMessages/Error/ErrorMessages';

type Jsonable =
  | string
  | number
  | boolean
  | null
  | undefined
  | readonly Jsonable[]
  | { readonly [key: string]: Jsonable }
  | { toJSON(): Jsonable };

type FetchOptions = {
  method?: string;
  headers?: Record<string, string>;
  body?: Jsonable;
};

export const useAuthenticatedFetch = () => {
  const errorMessage = useMemo(() => new ErrorMessage(), []);
  const navigate = useNavigate();
  const { refreshToken } = useRefreshToken();

  const authenticationFetch = async <T>(
    url: string,
    options: FetchOptions = {},
  ): Promise<T> => {
    let accessToken = Cookies.get('accessToken');
    console.log(accessToken);

    if (!accessToken) {
      await refreshToken();
      accessToken = Cookies.get('accessToken');
      if (!accessToken) {
        navigate('/login');
        errorMessage.HTTP401();
        throw new Error('unAuth');
      }
    }

    const headers = {
      ...options.headers,
      Authorization: `Bearer ${accessToken}`,
      Accept: 'application/json',
    };

    try {
      console.log('url', url);
      const response = await fetch(url, {
        method: options.method || 'GET',
        credentials: 'include',
        headers,
        ...(options.body && { body: JSON.stringify(options.body) }),
      });

      if (response.status == 401) {
        await refreshToken();
        accessToken = Cookies.get('accessToken');
        if (!accessToken) {
          navigate('/login');
          throw new Error('unAuth');
        }
        return authenticationFetch(url, options);
      }

      if (response.status == 403) {
        errorMessage.HTTP403();
        throw new Error(`${response.status} ${response.text}`);
      }
      if (!response.ok) {
        throw new Error(`Http error, status ${response.status}`);
      }

      return await response.json();
    } catch (error) {
      throw new Error(`${error}`);
    }
  };

  return { authenticationFetch };
};
