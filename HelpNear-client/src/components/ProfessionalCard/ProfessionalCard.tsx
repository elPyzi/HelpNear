import styles from './ProfessionalCard.module.scss';
import { Professional } from '@/types/Users';
import StarIcon from '@assets/images/svg/star.svg';

type ProfessionalCardProps = Pick<
  Professional,
  'avatar' | 'fullName' | 'rating' | 'info'
>;

const ProfessionalCard = ({
  avatar,
  fullName,
  info,
  rating,
}: ProfessionalCardProps) => {
  return (
    <>
      <div className={styles['professional']}>
        <img src={avatar} alt="" />
        <div className={styles['professional__info']}>
          <p className={styles['professional__name']}>{fullName}</p>
          <p className={styles['professional__rating']}>
            {rating} <StarIcon />
          </p>
          <p className={styles['professional__description']}>{info}</p>
        </div>
      </div>
    </>
  );
};

export default ProfessionalCard;
