import styles from './Centers.module.scss';
import { useQuery } from '@tanstack/react-query';
import { Center } from '@/types/Centers';
import CenterCard from '@/components/CenterCard/CenterCard';
import Loading from '@/components/Loading/Loading';

import { API_CONFIG } from '@/api/api.config';

const getCenters = async (): Promise<Center[]> => {
  const response = await fetch(
    `${API_CONFIG.BASE_URL}${API_CONFIG.ENDPOINTS.GLOBALS.GET_SUPPORT_CENTER}`,
    {
      method: 'GET',
      credentials: 'include',
      headers: {
        Accept: 'application/json',
      },
    },
  );

  if (!response.ok) {
    throw new Error('Failed to fetch centers');
  }

  return await response.json();
};

const Centers = () => {
  const {
    data: centers,
    isLoading,
    error,
  } = useQuery({
    queryKey: ['centers'],
    queryFn: getCenters,
    retry: false,
  });

  if (isLoading) {
    return <Loading />;
  }

  if (error) {
    return <div>Error loading centers</div>;
  }

  return (
    <div className="container">
      <div className={styles['centers']}>
        {centers?.map((center) => <CenterCard key={center.name} {...center} />)}
      </div>
    </div>
  );
};

export default Centers;
