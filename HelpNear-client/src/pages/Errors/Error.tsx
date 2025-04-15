import styles from './Error.module.scss';
import { useParams, useLocation, Link } from 'react-router-dom';
import { PAGE_CONFIG } from '@/config/page.config';

import ErrorImg from '@assets/images/error.jpeg';

const Error = () => {
  const { type } = useParams<{ type: string }>();
  const location = useLocation();

  const titleCode: string = type || '404';
  const {
    message = 'Похоже у вас ошибка',
    from = { pathname: PAGE_CONFIG.HOME },
  } = location.state || {};

  return (
    <div className={styles['error']}>
      <img src={ErrorImg} alt="Ошибочка" className={styles['error__img']} />
      <h1 className={styles['error__title']}>{titleCode}</h1>
      <p className={styles['error__message']}>{message}</p>
      <div className={styles['error__btns']}>
        <Link to={from.pathname} className={styles['error__btn']}>
          Вернуться
        </Link>
        <Link to={PAGE_CONFIG.HOME} className={styles['error__btn']}>
          На главную
        </Link>
      </div>
    </div>
  );
};

export default Error;
