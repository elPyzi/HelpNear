import styles from './ClientsProblems.module.scss';

import Cookies from 'js-cookie';
import { useQuery } from '@tanstack/react-query';

import { API_CONFIG } from '@/api/api.config';

import { ensureError } from '@/utils/errorHandler';
import { BaseError } from '@/utils/Errors/BaseError';
import Loading from '@/components/Loading/Loading';

type Conclusion = {
  title: string;
  textInfo: string;
  professional: {
    fullName: string;
    contactNumber: string;
  };
};

const getConclusion = async (): Promise<Conclusion> => {
  try {
    const accessToken = Cookies.get('accessToken');
    const response = await fetch(
      `${API_CONFIG.BASE_URL}${API_CONFIG.ENDPOINTS.CLIENT.GET_CONCLUSION}`,
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
};

const ClientsProblems = () => {
  const {
    data: conclusion,
    isLoading,
    error,
  } = useQuery<Conclusion>({
    queryKey: ['conclusion'],
    queryFn: getConclusion,
  });

  if (isLoading) {
    return <Loading />;
  }

  if (error) {
    return <div>Ошибка при загрузке данных</div>;
  }

  return (
    <div className={styles.container}>
      <div className={styles.card}>
        <h2 className={styles.title}>{conclusion?.title}</h2>

        <div className={styles.content}>
          <p className={styles.text}>{conclusion?.textInfo}</p>
        </div>

        <div className={styles.footer}>
          <div className={styles.professionalInfo}>
            <h3 className={styles.professionalTitle}>
              Рекомендуемый специалист
            </h3>
            <p className={styles.professionalName}>
              {conclusion?.professional.fullName}
            </p>
            <a
              href={`tel:${conclusion?.professional.contactNumber}`}
              className={styles.professionalContact}
            >
              {conclusion?.professional.contactNumber}
            </a>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ClientsProblems;
