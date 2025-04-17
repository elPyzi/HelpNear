import Cookies from 'js-cookie';

import { ensureError } from './errorHandler';
import { BaseError } from './Errors/BaseError';

type TCheckAccessToken = {
  refreshToken: () => void;
  cb: () => void;
};

export const checkAccessToken = async ({
  refreshToken,
  cb,
}: TCheckAccessToken) => {
  let accessToken = Cookies.get('accessToken');

  if (!accessToken) {
    try {
      await refreshToken();
      accessToken = Cookies.get('accessToken');
      if (!accessToken) {
        console.info('Токенов нету');
        return null;
      }
      return await cb();
    } catch (err) {
      const error = ensureError(err);
      throw new BaseError('Error with checking access token', {
        cause: error,
        context: { file: 'checkAccessToken' },
      });
    }
  }

  return await cb();
};
