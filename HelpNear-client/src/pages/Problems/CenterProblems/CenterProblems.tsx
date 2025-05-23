import { API_CONFIG } from '@/api/api.config';
import Loading from '@/components/Loading/Loading';
import { ensureError } from '@/utils/errorHandler';
import { BaseError } from '@/utils/Errors/BaseError';
import { useQuery } from '@tanstack/react-query';
import styles from './CenterProblems.module.scss';
import { useState } from 'react';
import AssignSpecialistModal from '@/components/Modals/AssignSpecialistModal/AssignSpecialistModal';

import Cookies from 'js-cookie';

type Problems = {
  userId: number;
  fullName: string;
  title: string;
  description: string;
};

const getProblems = async (): Promise<Problems[]> => {
  try {
    const accessToken = Cookies.get('accessToken');
    const response = await fetch(
      `${API_CONFIG.BASE_URL}${API_CONFIG.ENDPOINTS.SUPPORT_CENTER.GET_PROBLEMS}`,
      {
        method: 'GET',
        credentials: 'include',
        headers: {
          Authorization: `Bearer ${accessToken}`,
          Accept: 'application/json',
        },
      },
    );
    return await response.json();
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

const CenterProblems = () => {
  const [selectedProblem, setSelectedProblem] = useState<Problems | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const {
    data: problems,
    isLoading,
    refetch,
  } = useQuery<Problems[]>({
    queryKey: ['problems'],
    queryFn: getProblems,
    retry: false,
    refetchInterval: 20000,
  });

  const handleAssignSpecialist = (problem: Problems) => {
    setSelectedProblem(problem);
    setIsModalOpen(true);
  };

  if (isLoading) return <Loading />;

  return (
    <div className={styles['problems']}>
      <h2 className={styles['problems__title']}>Проблемы</h2>
      <div className={styles['problems__table']}>
        <table>
          <thead>
            <tr>
              <th>Имя</th>
              <th>Заголовок</th>
              <th>Описание</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {problems?.map((problem) => (
              <tr key={problem.userId}>
                <td>{problem.fullName}</td>
                <td>{problem.title}</td>
                <td>{problem.description}</td>
                <td>
                  <button
                    className={styles['problems__assign-btn']}
                    onClick={() => handleAssignSpecialist(problem)}
                  >
                    Назначить специалиста
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      {isModalOpen && selectedProblem && (
        <AssignSpecialistModal
          problem={selectedProblem}
          onClose={() => setIsModalOpen(false)}
          refetch={refetch}
        />
      )}
    </div>
  );
};

export default CenterProblems;
