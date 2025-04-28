import { Outlet, Link, useNavigate } from 'react-router-dom';
import { useAppDispatch } from '@/hooks/reduxHooks';
import { useAppSelector } from '@/hooks/reduxHooks';
import { logout } from '@/store/Slices/auth.slice';
import { PAGE_CONFIG } from '@/config/page.config';

import styles from './Account.module.scss';
import { ROLES } from '@/api/ROLES';

const Account = () => {
  const { user } = useAppSelector((state) => state.auth);
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const handleLogout = () => {
    dispatch(logout());
    navigate(PAGE_CONFIG.HOME);
  };

  return (
    <div className={styles['account']}>
      <aside className={styles['account__aside']}>
        <nav className={styles['account__nav']}>
          <Link
            to={`/${PAGE_CONFIG.ACCOUNT}`}
            className={styles['account__link']}
          >
            Профиль
          </Link>
          {user?.role !== ROLES.ADMIN && (
            <Link to={PAGE_CONFIG.PROBLEMS} className={styles['account__link']}>
              Проблемы
            </Link>
          )}
          {user?.role === ROLES.ADMIN && (
            <>
              <Link
                to={PAGE_CONFIG.ADD_CENTERS}
                className={styles['account__link']}
              >
                Добавление центров
              </Link>
              <Link to={PAGE_CONFIG.USERS} className={styles['account__link']}>
                Пользователи
              </Link>
            </>
          )}
        </nav>
        <button onClick={handleLogout} className={styles['account__logout']}>
          Выйти
        </button>
        <button onClick={() => navigate({ pathname: PAGE_CONFIG.HOME })}>
          Вернуться
        </button>
      </aside>
      <main className={styles['account__content']}>
        <Outlet />
      </main>
    </div>
  );
};

export default Account;
