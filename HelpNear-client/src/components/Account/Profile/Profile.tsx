import styles from './Profile.module.scss';

import { useAppSelector } from '@/hooks/reduxHooks';
import { User } from '@/types/Users';

import { useState } from 'react';
import { useForm } from 'react-hook-form';

type UserData = Omit<User, 'id' | 'login' | 'role'>;

const Profile = () => {
  const { user } = useAppSelector((state) => state.auth);
  const [needUpdateData, setNeedUpdateData] = useState<boolean>(false);
  const { register, handleSubmit } = useForm<UserData>({
    defaultValues: {
      fullName: user?.fullName,
      email: user?.email,
      address: user?.address,
    },
  });

  const onSubmit = () => {};

  return (
    <form className={styles['profile']} onSubmit={handleSubmit(onSubmit)}>
      <div className={styles['profile__avatar']}>
        <label htmlFor="avatar" className={styles['profile__info-label']}>
          <input
            type="text"
            id="avatar"
            disabled={!needUpdateData}
            {...register('avatar')}
            className={styles['profile__info-inp']}
          />
        </label>
      </div>
      <div className={styles['profile__user-info']}>
        <label htmlFor="fullName" className={styles['profile__info-label']}>
          <input
            type="text"
            id="fullName"
            disabled={!needUpdateData}
            {...register('fullName')}
            className={styles['profile__info-inp']}
          />
        </label>
        <label htmlFor="email" className={styles['profile__info-label']}>
          <input
            type="text"
            id="email"
            disabled={!needUpdateData}
            {...register('email')}
            className={styles['profile__info-inp']}
          />
        </label>
        <label
          htmlFor="contactNumber"
          className={styles['profile__info-label']}
        >
          <input
            type="text"
            id="contactNumber"
            disabled={!needUpdateData}
            {...register('contactNumber')}
            className={styles['profile__info-inp']}
          />
        </label>
        <label htmlFor="address" className={styles['profile__info-label']}>
          <input
            type="text"
            id="address"
            disabled={!needUpdateData}
            {...register('address')}
            className={styles['profile__info-inp']}
          />
        </label>
      </div>
      <button
        type={needUpdateData ? 'submit' : 'button'}
        onClick={() => setNeedUpdateData((prev) => !prev)}
      >
        {needUpdateData ? 'Сохранить' : 'Изменить данные'}
      </button>
    </form>
  );
};

export default Profile;
// Профиль Проблемы Пользователи Добавление центров
