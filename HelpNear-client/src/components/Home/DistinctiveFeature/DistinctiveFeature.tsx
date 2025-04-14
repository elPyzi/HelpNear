import styles from './DistinctiveFeature.module.scss';

import DistinctiveFeatureItem from './DistinctiveFeatureItem/DistinctiveFeatureItem';

import InnovationIcon from '@assets/images/svg/Distinctive Feature/innovation.svg';
import QualityIcon from '@assets/images/svg/Distinctive Feature/Quality.svg';
import CheckIcon from '@assets/images/svg/Distinctive Feature/check.svg';
import ServiceIcon from '@assets/images/svg/Distinctive Feature/service.svg';

const DistinctiveFeature = () => {
  return (
    <div className={styles['distinctive-feature']}>
      <DistinctiveFeatureItem
        title="Инновация"
        description="Мы используем инновационные подходы"
        Svg={InnovationIcon}
      />
      <DistinctiveFeatureItem
        title="Квалификация"
        description="Только высококвалифицированные специалисты"
        Svg={QualityIcon}
      />
      <DistinctiveFeatureItem
        title="Лучший продукт"
        description="Признан лучшим сервисом 2024"
        Svg={CheckIcon}
      />
      <DistinctiveFeatureItem
        title="Топ сервис"
        description="Лучший сервис. Быстро. Чётко."
        Svg={ServiceIcon}
      />
    </div>
  );
};

export default DistinctiveFeature;
