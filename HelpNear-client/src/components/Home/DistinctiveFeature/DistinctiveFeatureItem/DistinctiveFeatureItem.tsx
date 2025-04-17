import styles from './DistinctiveFeatureItem.module.scss';

type DistinctiveFeatureItemProps = {
  Svg: React.ReactNode;
  title: string;
  description: string;
};

const DistinctiveFeatureItem = ({
  Svg,
  title,
  description,
}: DistinctiveFeatureItemProps) => {
  return (
    <div className={styles['distinctive-feature-item']}>
      <Svg />
      <h4 className={styles['distinctive-feature-item__title']}>{title}</h4>
      <p className={styles['distinctive-feature-item__description']}>
        {description}
      </p>
    </div>
  );
};

export default DistinctiveFeatureItem;
