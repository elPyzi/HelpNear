import styles from './Login.module.scss';

import { Link } from 'react-router-dom';
import { PAGE_CONFIG } from '@/config/page.config';

import { useForm, SubmitHandler } from 'react-hook-form';
import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { ensureError } from '@/utils/errorHandler';
import { BaseError } from '@/utils/Errors/BaseError';
import { login } from '@/store/Slices/auth.slice';
import { useAppDispatch } from '@/hooks/reduxHooks';

import { API_CONFIG } from '@/api/api.config';

type LoginData = {
  authString: string;
  password: string;
};

const Login = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors, isValid },
  } = useForm<LoginData>();

  const { mutate: submitLogin } = useMutation({
    mutationFn: async (data: LoginData) => {
      try {
        const response = await fetch(
          `${API_CONFIG.BASE_URL}${API_CONFIG.ENDPOINTS.AUTH.LOGIN}`,
          {
            method: 'POST',
            credentials: 'include',
            headers: {
              'Content-type': 'application/json',
              Accept: 'application/json',
            },
            body: JSON.stringify({ data }),
          },
        );

        if (!response.ok)
          navigate(`${PAGE_CONFIG.ERROR}/${response.status}`, {
            state: {
              message: response.text,
              from: {
                pathname: `${API_CONFIG.BASE_URL}${API_CONFIG.ENDPOINTS.AUTH.LOGIN}`,
              },
            },
          });

        return await response.json();
      } catch (err) {
        const error = ensureError(err);

        throw new BaseError('Error with fetch login', {
          cause: error,
          context: {
            endpoint: `${API_CONFIG.BASE_URL}${API_CONFIG.ENDPOINTS.AUTH.LOGIN}`,
          },
        });
      }
    },
    onSuccess: async (data) => {
      await dispatch(login(data));
    },
  });

  const onSubmit: SubmitHandler<LoginData> = async (data) => {
    await submitLogin(data);
    reset();
  };

  return (
    <div className={styles['login']}>
      <form className={styles['login__form']} onSubmit={handleSubmit(onSubmit)}>
        <h1 className={styles['login__title']}>#HelpNear</h1>
        <input
          type="text"
          placeholder="Email или login"
          className={styles['login__inp']}
          tabIndex={0}
          {...register('authString', {
            required: true,
            minLength: {
              value: 5,
              message: 'Имя должно содержать больше 5 символов',
            },
            maxLength: 50,
            validate: (value) => {
              const isEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
              const isLogin = /^[a-zA-Z0-9_]{3,}$/.test(value);

              return isEmail || isLogin || 'Введите корректный email или логин';
            },
          })}
        />
        {errors.authString && (
          <p className={styles['login__error']}>{errors.authString.message}</p>
        )}
        <input
          type="password"
          placeholder="Пароль"
          className={styles['login__inp']}
          tabIndex={1}
          {...register('password', {
            required: 'Заполните пароль',
            minLength: {
              value: 8,
              message: 'Пароль должен содержать больше 8 символов',
            },
            maxLength: {
              value: 50,
              message: 'Пароль не стоит делать таким большим',
            },
          })}
        />
        {errors.password && (
          <p className={styles['login__error']}>{errors.password.message}</p>
        )}
        <button
          type="submit"
          className={`${styles['login__btn']} ${styles['login__btn-login']}`}
          tabIndex={2}
          disabled={!isValid}
        >
          Log in
        </button>
        <Link
          to={`/${PAGE_CONFIG.AUTH}/${PAGE_CONFIG.REGISTRATION}`}
          className={`${styles['login__btn']} ${styles['login__btn-reg']}`}
          tabIndex={3}
        >
          Sign Up
        </Link>
        <Link
          to={`/${PAGE_CONFIG.AUTH}/${PAGE_CONFIG.PASSWORD_RECOVERY}`}
          className={`${styles['login__btn']} ${styles['login__btn-forgot']}`}
          tabIndex={4}
        >
          Забыли пароль?
        </Link>
      </form>
      <button onClick={() => navigate(-1)} className={styles['return']}>
        На главную
      </button>
    </div>
  );
};

export default Login;
