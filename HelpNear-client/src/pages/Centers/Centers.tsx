import styles from './Centers.module.scss';
import { useQuery } from '@tanstack/react-query';
import { Center } from '@/types/Centers';
import CenterCard from '@/components/CenterCard/CenterCard';
import Loading from '@/components/Loading/Loading';

const getCenters = async (): Promise<Center[]> => {
  const response = await fetch('/globals/get-support-centers', {
    method: 'GET',
    credentials: 'include',
    headers: {
      Accept: 'application/json',
    },
  });

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
