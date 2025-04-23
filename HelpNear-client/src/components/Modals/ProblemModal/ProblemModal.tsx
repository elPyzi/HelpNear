import styles from './ProblemModal.module.scss';
import { useForm } from 'react-hook-form';
import { useMutation } from '@tanstack/react-query';
import { API_CONFIG } from '@/api/api.config';
import { ErrorMessage } from '@/utils/PushMessages/Error/ErrorMessages';

import Cookies from 'js-cookie';
import { BaseError } from '@/utils/Errors/BaseError';

type ProblemModalProps = {
  centerId: number;
  onClose: (close: boolean) => void;
};

type ProblemSubmission = {
  title: string;
  description: string;
  center_id: number;
};
// yyyy-MM-dd
const getCurrentDate = () => {
  const date = new Date();
  const currentDate = `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
  console.log(currentDate);
  return currentDate;
};

const submitProblem = async (data: ProblemSubmission) => {
  const accessToken = Cookies.get('accessToken');
  const receivedDate = getCurrentDate();
  const RequestData = { ...data, receivedDate };
  const response = await fetch(
    `${API_CONFIG.BASE_URL}${API_CONFIG.ENDPOINTS.CLIENT.MAKE_APPLICATION}`,
    {
      method: 'POST',
      credentials: 'include',
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(RequestData),
    },
  );

  if (!response.ok) {
    throw new Error('Failed to submit problem');
  }

  return response.json();
};

const ProblemModal = ({ centerId, onClose }: ProblemModalProps) => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<ProblemSubmission>();
  const errorMessage = new ErrorMessage();

  const { mutate } = useMutation({
    mutationFn: submitProblem,
    onSuccess: () => {
      onClose(false);
    },
    onError: (error) => {
      errorMessage.HTTP401();
      new BaseError('error with add problem', { cause: error });
    },
  });

  const onSubmit = (data: Omit<ProblemSubmission, 'center_id'>) => {
    mutate({
      ...data,
      center_id: centerId,
    });
  };

  return (
    <div className={styles['modal']}>
      <div className={styles['modal__content']}>
        <button
          className={styles['modal__close']}
          onClick={() => onClose(false)}
        >
          ✕
        </button>
        <h2>Опишите вашу проблему</h2>
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className={styles['modal__field']}>
            <label htmlFor="title">Заголовок проблемы</label>
            <input
              id="title"
              type="text"
              maxLength={25}
              {...register('title', { required: true })}
            />
            {errors.title && (
              <span className={styles['modal__error']}>
                Это поле обязательно
              </span>
            )}
          </div>
          <div className={styles['modal__field']}>
            <label htmlFor="description">Описание проблемы</label>
            <textarea
              id="description"
              {...register('description', { required: true })}
            />
            {errors.description && (
              <span className={styles['modal__error']}>
                Это поле обязательно
              </span>
            )}
          </div>
          <button type="submit" className={styles['modal__submit']}>
            Отправить
          </button>
        </form>
      </div>
    </div>
  );
};

export default ProblemModal;
