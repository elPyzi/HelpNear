import styles from './CenterCard.module.scss';
import { Link } from 'react-router-dom';
import { Center } from '@/types/Centers';
import StarIcon from '@assets/images/svg/star.svg';
import CenterImg from '@images/center.jpeg';

type CenterCardProps = Center;

const CenterCard = ({
  name,
  address,
  contact_number,
  email,
  rating,
}: CenterCardProps) => {
  return (
    <div className={styles['center']}>
      <img src={CenterImg} alt={name} />
      <div className={styles['center__info']}>
        <p className={styles['center__name']}>{name}</p>
        <p className={styles['center__rating']}>
          {rating} <StarIcon />
        </p>
        <p className={styles['center__description']}>{address}</p>
        <p className={styles['center__description']}>{contact_number}</p>
        <p className={styles['center__description']}>{email}</p>
        <Link to="" className={styles['center__more']}>
          Узнать больше
        </Link>
      </div>
    </div>
  );
};

export default CenterCard;
