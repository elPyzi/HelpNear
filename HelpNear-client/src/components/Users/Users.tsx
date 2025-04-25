import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { User } from '@/types/Users';
import Pagination from '../Pagination/Pagination';
import styles from './Users.module.scss';

const Users = () => {
  const [currentPage, setCurrentPage] = useState(1);
  const [usersPerPage] = useState(10);

  const { data: users, isLoading, error } = useQuery<User[]>({ queryKey: [] });

  if (isLoading) return <div>Загрузка...</div>;
  if (error) return <div>Произошла ошибка при загрузке пользователей</div>;
  if (!users) return null;

  const indexOfLastUser = currentPage * usersPerPage;
  const indexOfFirstUser = indexOfLastUser - usersPerPage;
  const currentUsers = users.slice(indexOfFirstUser, indexOfLastUser);

  const handleBanUser = async (userId: string) => {
    try {
      await fetch(`/api/users/${userId}/ban`, {
        method: 'POST',
      });
    } catch (error) {
      console.error('Ошибка при блокировке пользователя:', error);
    }
  };

  return (
    <div className={styles.users}>
      <h2 className={styles.users__title}>Управление пользователями</h2>
      <div className={styles.users__table}>
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Имя</th>
              <th>Email</th>
              <th>Роль</th>
              <th>Статус</th>
              <th>Действия</th>
            </tr>
          </thead>
          <tbody>
            {currentUsers.map((user) => (
              <tr key={user.id}>
                <td>{user.id}</td>
                <td>{user.fullName}</td>
                <td>{user.email}</td>
                <td>{user.role}</td>
                <td>{user.isBanned ? 'Заблокирован' : 'Активен'}</td>
                <td>
                  <button
                    className={styles.users__action}
                    onClick={() => handleBanUser(user.id)}
                  >
                    {user.isBanned ? 'Разблокировать' : 'Заблокировать'}
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <Pagination
        currentPage={currentPage}
        totalPages={Math.ceil(users.length / usersPerPage)}
        onPageChange={setCurrentPage}
      />
    </div>
  );
};

export default Users;
