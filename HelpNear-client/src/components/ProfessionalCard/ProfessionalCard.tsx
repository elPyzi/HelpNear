import styles from './ProfessionalCard.module.scss';
import { Link } from 'react-router-dom';

import { Professional } from '@/types/Users';

import StarIcon from '@assets/images/svg/star.svg';

type ProfessionalCardProps = Pick<
  Professional,
  'avatar' | 'full_name' | 'rating' | 'info'
>;

const ProfessionalCard = ({
  avatar,
  full_name,
  info,
  rating,
}: ProfessionalCardProps) => {
  return (
    <div className={styles['professional']}>
      <img src={avatar} alt="" />
      <div className={styles['professional__info']}>
        <p className={styles['professional__name']}>{full_name}</p>
        <p className={styles['professional__rating']}>
          {rating} <StarIcon />
        </p>
        <p className={styles['professional__description']}>{info}</p>
        <Link to="" className={styles['professional__more']}>
          Узнать больше
        </Link>
      </div>
    </div>
  );
};

export default ProfessionalCard;
