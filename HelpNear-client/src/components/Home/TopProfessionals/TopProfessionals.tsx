import styles from './TopProfessionals.module.scss';

import { API_CONFIG } from '@/api/api.config';
import { ensureError } from '@/utils/errorHandler';

import Loading from '@/components/Loading/Loading';

import { Professional } from '@types/Users';
type ProfessionalResponse = Pick<
  Professional,
  'id' | 'avatar' | 'full_name' | 'rating' | 'info'
>;

import { useQuery } from '@tanstack/react-query';
import { Swiper, SwiperSlide } from 'swiper/react';

import { professionals } from '@/mock/data';
import ProfessionalCard from '@/components/ProfessionalCard/ProfessionalCard';

const TopProfessionals = () => {
  // const getProfessionals = async () => {
  //   try {
  //     const response = await fetch(
  //       `${API_CONFIG.BASE_URL}${API_CONFIG.ENDPOINTS.GLOBALS.GET_PROFESSIONALS}/homepage`,
  //       {
  //         method: 'GET',
  //         headers: {
  //           Accept: 'application/json',
  //         },
  //       },
  //     );
  //     return await response.json();
  //   } catch (err) {
  //     const error = ensureError(err);
  //     console.error(error.message);
  //   }
  // };

  // const {
  //   data: professionals,
  //   isError,
  //   isLoading,
  // } = useQuery<ProfessionalResponse[]>({
  //   queryKey: ['professionals'],
  //   queryFn: getProfessionals,
  //   retry: false,
  // });

  // if (isLoading) return <Loading />;

  // console.log(professionals);

  return (
    <>
      {professionals?.map((professional) => (
        <ProfessionalCard
          avatar={professional.avatar}
          full_name={professional.full_name}
          rating={professional.rating}
          info={professional.info}
        />
      ))}
    </>
  );
};

export default TopProfessionals;
