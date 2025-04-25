import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import Loading from '../../../components/Loading/Loading';
import AssignTreatmentModal from '../../../components/Modals/AssignTreatmentModal/AssignTreatmentModal';
import styles from './ProfessionalProblem.module.scss';
import { API_CONFIG } from '@/api/api.config';
import { ensureError } from '@/utils/errorHandler';
import { BaseError } from '@/utils/Errors/BaseError';
import Cookies from 'js-cookie';

type Problems = {
  userId: number;
  fullName: string;
  title: string;
};

const ProfessionalProblem = () => {
  const [selectedProblemId, setSelectedProblemId] = useState<number>(0);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const { data: problems, isLoading } = useQuery<Problems[]>({
    queryKey: ['problems'],
    queryFn: async (): Promise<Problems[]> => {
      const accessToken = Cookies.get('accessToken');
      try {
        const response = await fetch(
          `${API_CONFIG.BASE_URL}${API_CONFIG.ENDPOINTS.PROFESSIONAL.GET_USER_PROBLEMS}}`,
          {
            method: 'GET',
            credentials: 'include',
            headers: {
              Authorization: `Bearer ${accessToken}`,
              Accept: 'application/json',
            },
          },
        );
        const data = await response.json();
        return data;
      } catch (error) {
        const err = ensureError(error);
        throw new BaseError('', { cause: err });
      }
    },
  });

  const handleAssignTreatment = (userId: number) => {
    setSelectedProblemId(userId);
    setIsModalOpen(true);
  };

  if (isLoading) return <Loading />;

  return (
    <div className={styles['problems']}>
      <h2 className={styles['problems__title']}>Проблемы пациентов</h2>
      <div className={styles['problems__table']}>
        <table>
          <thead>
            <tr>
              <th>Имя пациента</th>
              <th>Проблема</th>
            </tr>
          </thead>
          <tbody>
            {problems?.map((problem) => (
              <tr key={problem.userId}>
                <td>{problem.fullName}</td>
                <td>{problem.title}</td>

                <td>
                  <button
                    className={styles['problems__assign-btn']}
                    onClick={() => handleAssignTreatment(problem.userId)}
                  >
                    Назначить лечение
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      {isModalOpen && (
        <AssignTreatmentModal
          userId={selectedProblemId}
          onClose={() => setIsModalOpen(false)}
        />
      )}
    </div>
  );
};

export default ProfessionalProblem;
