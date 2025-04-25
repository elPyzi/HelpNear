import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import styles from './AssignSpecialistModal.module.scss';
import { API_CONFIG } from '@/api/api.config';
import Cookies from 'js-cookie';
import { BaseError } from '@/utils/Errors/BaseError';
import { ensureError } from '@/utils/errorHandler';
import Loading from '@/components/Loading/Loading';

type Specialist = {
  id: number;
  name: string;
};

type AssignSpecialistModalProps = {
  problem: {
    userId: number;
    title: string;
  };
  onClose: () => void;
};

const getSpecialists = async (): Promise<Specialist[]> => {
  const accessToken = Cookies.get('accessToken');
  const response = await fetch(
    `${API_CONFIG.BASE_URL}${API_CONFIG.ENDPOINTS.SUPPORT_CENTER.GET_PROFESSIONALS}`,
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
};

const AssignSpecialistModal = ({
  problem,
  onClose,
}: AssignSpecialistModalProps) => {
  const [selectedSpecialist, setSelectedSpecialist] = useState<number | null>(
    null,
  );

  const { data: specialists, isLoading } = useQuery<Specialist[]>({
    queryKey: ['specialists'],
    queryFn: getSpecialists,
  });

  const handleAssign = async () => {
    if (!selectedSpecialist) return;
    const accessToken = Cookies.get('accessToken');

    try {
      const response = await fetch(
        `${API_CONFIG.BASE_URL}${API_CONFIG.ENDPOINTS.SUPPORT_CENTER.SET_PROFESSIONAL}`,
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${accessToken}`,
          },
          body: JSON.stringify({
            userId: problem.userId,
            professionalId: selectedSpecialist,
          }),
        },
      );

      if (!response.ok) return;

      onClose();
    } catch (error) {
      const err = ensureError(error);
      throw new BaseError('error with add problem', { cause: err });
    }
  };

  if (isLoading) return <Loading />;

  return (
    <div className={styles.modal}>
      <div className={styles.modal__content}>
        <button className={styles.modal__close} onClick={onClose}>
          ×
        </button>
        <h2>Назначить специалиста</h2>
        <p className={styles.modal__problem}>Проблема: {problem.title}</p>
        <div className={styles.modal__specialists}>
          {specialists?.map((specialist) => (
            <div
              key={specialist.id}
              className={`${styles.modal__specialist} ${
                selectedSpecialist === specialist.id
                  ? styles['modal__specialist--selected']
                  : ''
              }`}
              onClick={() => setSelectedSpecialist(specialist.id)}
            >
              <h3>{specialist.name}</h3>
            </div>
          ))}
        </div>
        <button
          className={styles.modal__assign}
          onClick={handleAssign}
          disabled={!selectedSpecialist}
        >
          Назначить
        </button>
      </div>
    </div>
  );
};

export default AssignSpecialistModal;
