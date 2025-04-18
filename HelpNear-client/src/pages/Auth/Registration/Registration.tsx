import styles from './Registration.module.scss';

import AvatarPhoto from '@images/svg/avatar-photo.svg';

import { API_CONFIG } from '@/api/api.config';
import { PAGE_CONFIG } from '@/config/page.config';
import { ensureError } from '@utils/errorHandler';
import { BaseError } from '@utils/Errors/BaseError';
import { ErrorMessage } from '@utils/PushMessages/Error/ErrorMessages';

import { useState, useMemo } from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { useMutation } from '@tanstack/react-query';

import { User } from '@/types/Users';

type RegistrationData = Omit<User, 'role' | 'id'>;

const Registration = () => {
  const [page, setPage] = useState<boolean>(true);
  const navigate = useNavigate();
  const pushMessage = useMemo(() => new ErrorMessage(), []);

  const {
    register,
    handleSubmit,
    formState: { isValid },
    reset,
  } = useForm<RegistrationData>();

  const { mutate: submitRegister } = useMutation({
    mutationFn: async (data: RegistrationData) => {
      try {
        const response = await fetch(
          `${API_CONFIG.BASE_URL}${API_CONFIG.ENDPOINTS.AUTH.REGISTER}`,
          {
            method: 'POST',
            headers: {
              'Content-type': 'application/json',
            },
            body: JSON.stringify({ data }),
          },
        );

        if (!response.ok)
          navigate(`${PAGE_CONFIG.ERROR}/${response.status}`, {
            state: {
              message: response.text,
              from: {
                pathname: `${API_CONFIG.BASE_URL}${API_CONFIG.ENDPOINTS.AUTH.REGISTER}`,
              },
            },
          });

        if (response.status == 409) {
          const errorMessage = await response.text();
          pushMessage.HTTP409(errorMessage);
        }
      } catch (err) {
        const error = ensureError(err);
        throw new BaseError('Error with fetch reg', {
          cause: error,
          context: {
            endpoint: `${API_CONFIG.BASE_URL}${API_CONFIG.ENDPOINTS.AUTH.REGISTER}`,
          },
        });
      }
    },
    onSuccess: () => {
      setTimeout(() => {
        navigate(PAGE_CONFIG.HOME);
      }, 1000);
    },
  });

  const onSubmit: SubmitHandler<RegistrationData> = async (data) => {
    await submitRegister(data);
    reset();
  };

  return (
    <div className={styles['registration']}>
      <form
        className={styles['registration__form']}
        onSubmit={handleSubmit(onSubmit)}
      >
        <h1 className={styles['registration__title']}>#Registration</h1>
        {page && (
          <>
            <input
              type="email"
              placeholder="Email"
              className={`${styles['registration__inp']} ${styles['registration__inp-email']}`}
              tabIndex={0}
              {...register('email', {
                // required: true,
                // minLength: {
                //   value: 9,
                //   message: 'Не верный email',
                // },
                // maxLength: 50,
                // pattern: /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/,
              })}
            />
            <input
              placeholder="login"
              className={styles['registration__inp']}
              {...register('login', {
                // required: 'Заполните поле логина',
                // minLength: {
                //   value: 8,
                //   message: 'Логин должен содержать больше 5 символов',
                // },
                // maxLength: 30,
                // pattern: /^[a-zA-Z0-9]+$/,
              })}
            />
            <input
              type="password"
              placeholder="Пароль"
              className={styles['registration__inp']}
              tabIndex={1}
              {...register('password', {
                // required: 'Заполните пароль',
                // minLength: {
                //   value: 8,
                //   message: 'Пароль должен содержать больше 8 символов',
                // },
                // maxLength: {
                //   value: 50,
                //   message: 'Пароль не стоит делать таким большим',
                // },
                // pattern: /^[a-zA-Z0-9!@#$%^&*()_+-]+$/,
              })}
            />
          </>
        )}

        <button
          type='button'
          onClick={() => setPage((prev) => !prev)}
          className={`${styles['registration__btn']} ${styles['registration__btn-return']}`}
        >
          {page ? 'Далее' : 'На 1 страницу'}
        </button>

        {!page && (
          <>
            <input
              type="text"
              placeholder="Ваше полное имя"
              className={`${styles['registration__inp']} ${styles['registration__inp-email']}`}
              {...register('full_name', {
                // required: 'Заполните поля имени',
                // minLength: {
                //   value: 5,
                //   message: 'Имя должно быть 5 символов',
                // },
                // maxLength: 50,
                // pattern: /^[a-zA-Zа-яА-ЯёЁ]+$/,
              })}
            />
            <input
              type="text"
              placeholder="Адрес"
              className={styles['registration__inp']}
              {...register('address', {
                // required: 'Заполните поле адреса',
                // minLength: 5,
                // maxLength: 70,
                // pattern: /^[a-zA-Zа-яА-ЯёЁ]+$/,
              })}
            />
            <input
              type="text"
              placeholder="tel"
              className={styles['registration__inp']}
              {...register('contact_number')}
            />
            {/* <label htmlFor="avatar" className={styles['registration__avatar']}>
              <AvatarPhoto />
              <input
                type="file"
                id="avatar"
                className={styles['registration__avatar-inp']}
                {...register('avatar')}
              />
            </label> */}
            <button
              type="submit"
              className={`${styles['registration__btn']} ${styles['registration__btn-join']}`}
              tabIndex={2}
              disabled={!isValid}
            >
              Присоединится
            </button>
          </>
        )}
      </form>
      <button onClick={() => navigate(-1)} className={styles['return']}>
        Назад
      </button>
    </div>
  );
};

export default Registration;
