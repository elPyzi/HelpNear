import styles from './CenterDetails.module.scss';
import { useQuery } from '@tanstack/react-query';
import { useParams } from 'react-router-dom';
import { Center } from '@/types/Centers';
import { Professional } from '@/types/Users';
import ProfessionalCard from '@/components/ProfessionalCard/ProfessionalCard';
import StarIcon from '@assets/images/svg/star.svg';
import Loading from '@/components/Loading/Loading';

import { API_CONFIG } from '@/api/api.config';

import CenterImg from '@images/center.jpeg';
import { useState } from 'react';
import ProblemModal from '@/components/Modals/ProblemModal/ProblemModal';

const getCenterDetails = async (
  centerId: string,
): Promise<{
  center: Center;
  professionals: Professional[];
}> => {
  const response = await fetch(
    `${API_CONFIG.BASE_URL}${API_CONFIG.ENDPOINTS.GLOBALS.GET_SUPPORT_CENTER}/${centerId}`,
    {
      method: 'GET',
      credentials: 'include',
      headers: {
        Accept: 'application/json',
      },
    },
  );

  if (!response.ok) {
    throw new Error('Failed to fetch center details');
  }

  return await response.json();
};

const CenterDetails = () => {
  const [isSubmissionModalOpen, setSubmissionModal] = useState<boolean>(false);
  const { id } = useParams();
  console.log(typeof id, id);
  const {
    data: centerData,
    isLoading,
    error,
  } = useQuery({
    queryKey: ['center', id],
    queryFn: () => getCenterDetails(id!),
    enabled: !!id,
  });

  if (isLoading) return <Loading />;
  if (error) return <div>Error loading center details</div>;
  if (!centerData) return null;

  const { center, professionals } = centerData;

  return (
    <div className="container">
      <div className={styles['center-details']}>
        <div className={styles['center-details__info']}>
          <img src={CenterImg} alt="" />
          <h2 className={styles['center-details__name']}>{center.name}</h2>
          <div className={styles['center-details__rating']}>
            {center.rating} <StarIcon />
          </div>
          <p className={styles['center-details__address']}>{center.address}</p>
          <p className={styles['center-details__contact']}>
            {center.contact_number}
          </p>
          <p className={styles['center-details__email']}>{center.email}</p>
          <button type="button" onClick={() => setSubmissionModal(true)}>
            Оставить заявку в центр
          </button>
          {isSubmissionModalOpen && (
            <ProblemModal centerId={center.id} onClose={setSubmissionModal} />
          )}
        </div>

        <div className={styles['center-details__professionals']}>
          <h3>Наши специалисты</h3>
          <div className={styles['center-details__professionals-list']}>
            {professionals.map((professional) => (
              <ProfessionalCard key={professional.id} {...professional} />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default CenterDetails;
