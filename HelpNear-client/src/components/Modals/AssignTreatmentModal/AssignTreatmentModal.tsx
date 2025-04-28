import styles from './AssignTreatmentModal.module.scss';
import { useQuery } from '@tanstack/react-query';
import { ensureError } from '@/utils/errorHandler';
import { BaseError } from '@/utils/Errors/BaseError';
import { API_CONFIG } from '@/api/api.config';

import { useForm } from 'react-hook-form';
import Cookies from 'js-cookie';

type Problem = {
  fullName: string;
  title: string;
  description: string;
};

interface AssignTreatmentModalProps {
  userId: number;
  onClose: () => void;
}

type ConclusionForm = {
  conclusion: string;
};

const AssignTreatmentModal: React.FC<AssignTreatmentModalProps> = ({
  userId,
  onClose,
}) => {
  console.log(userId);
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<ConclusionForm>();
  const { data: problem } = useQuery<Problem>({
    queryKey: ['problem'],
    queryFn: async (): Promise<Problem> => {
      const accessToken = Cookies.get('accessToken');
      try {
        const response = await fetch(
          `${API_CONFIG.BASE_URL}${API_CONFIG.ENDPOINTS.PROFESSIONAL.GET_USER_PROBLEMS}/${userId}`,
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
      } catch (error) {
        const err = ensureError(error);
        throw new BaseError('', { cause: err });
      }
    },
  });

  const onSubmit = async (data: ConclusionForm) => {
    const accessToken = Cookies.get('accessToken');
    const DataRequest = { ...data, userId };
    try {
      const response = await fetch(
        `${API_CONFIG.BASE_URL}${API_CONFIG.ENDPOINTS.PROFESSIONAL.MAKE_JUDGMENT}`,
        {
          method: 'POST',
          credentials: 'include',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${accessToken}`,
          },
          body: JSON.stringify(DataRequest),
        },
      );
      if (!response.ok) return;
      onClose();
    } catch (error) {
      const err = ensureError(error);
      throw new BaseError('', { cause: err });
    }
  };

  return (
    <div className={styles.modal}>
      <div className={styles['modal__content']}>
        <button className={styles['modal__close']} onClick={onClose}>
          ×
        </button>
        <h2>Назначение лечения</h2>
        <div className={styles['modal__problem']}>
          <h3>Информация о проблеме:</h3>
          <p>Пациент: {problem?.fullName}</p>
          <p>Проблема: {problem?.title}</p>
          <p>Описание: {problem?.description}</p>
        </div>
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className={styles['modal__field']}>
            <label htmlFor="treatment">Назначенное лечение:</label>
            <textarea
              id="treatment"
              {...register('conclusion', {
                required: 'Поле должно быть заполнено',
              })}
            />
          </div>
          {errors.conclusion && <p>{errors.conclusion.message}</p>}
          <button type="submit" className={styles['modal__submit']}>
            Назначить лечение
          </button>
        </form>
      </div>
    </div>
  );
};

export default AssignTreatmentModal;
