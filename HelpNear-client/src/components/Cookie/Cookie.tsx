import styles from './Cookie.module.scss';

import CookieIcon from '@assets/images/svg/cookie.svg';

import { useState } from 'react';

const Cookie = () => {
  const [cookieWindow, setCookieWindow] = useState<boolean>(() => {
    if (
      !localStorage.getItem('COOKIE_ENABLED') ||
      localStorage.getItem('COOKIE_ENABLED') === 'disenable' ||
      localStorage.getItem('COOKIE_ENABLED') === ''
    ) {
      return true;
    }
    return false;
  });

  console.log(cookieWindow);

  const handleCookieEnable = (choose: string) => {
    localStorage.setItem('COOKIE_ENABLED', choose);
    setCookieWindow(false);
  };

  return (
    <>
      {cookieWindow && (
        <div className={styles['cookie']}>
          <CookieIcon className={styles['cookie__icon']} />
          <h2 className={styles['cookie__title']}>Мы используем cookie</h2>
          <p className={styles['cookie__info']}>
            Этот сайт использует cookie для вашего лучшего опыта работы
          </p>
          <button
            type="button"
            className={`${styles['cookie__btn']} ${styles['accept']}`}
            onClick={() => handleCookieEnable('enable')}
          >
            Принять
          </button>
          <button
            type="button"
            className={`${styles['cookie__btn']} ${styles['deny']}`}
            onClick={() => handleCookieEnable('disenable')}
          >
            Я на диете
          </button>
        </div>
      )}
    </>
  );
};

export default Cookie;
