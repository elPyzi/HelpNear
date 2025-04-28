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
  fullNamePro: string;
  contactNumberPro: string;
};

const getConclusion = async (): Promise<Conclusion[]> => {
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

    if (!response.ok) throw new Error('Network response was not ok');
    return await response.json();
  } catch (error) {
    const err = ensureError(error);
    throw new BaseError('Failed to fetch conclusions', { cause: err });
  }
};

const ClientsProblems = () => {
  const {
    data: conclusions,
    isLoading,
    error,
  } = useQuery<Conclusion[]>({
    queryKey: ['conclusions'],
    queryFn: getConclusion,
    retry: false,
  });

  if (!conclusions) {
    <h1>У вас истории проблем</h1>;
  }

  if (isLoading) {
    return <Loading />;
  }

  if (error) {
    return <div>Ошибка при загрузке данных</div>;
  }

  return (
    <div className={styles.container}>
      {conclusions?.map((conclusion, index) => (
        <div key={index} className={styles.card}>
          <h2 className={styles.title}>{conclusion.title}</h2>

          <div className={styles.content}>
            <p className={styles.text}>{conclusion.textInfo}</p>
          </div>

          <div className={styles.footer}>
            <div className={styles.professionalInfo}>
              <h3 className={styles.professionalTitle}>
                Рекомендуемый специалист
              </h3>
              <p className={styles.professionalName}>
                {conclusion.fullNamePro}
              </p>
              <p className={styles.professionalContact}>
                {conclusion.contactNumberPro}
              </p>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};

export default ClientsProblems;
