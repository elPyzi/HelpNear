import styles from './DistinctiveFeature.module.scss';

import DistinctiveFeatureItem from './DistinctiveFeatureItem/DistinctiveFeatureItem';

import InnovationIcon from '@assets/images/svg/Distinctive Feature/innovation.svg';
import QualityIcon from '@assets/images/svg/Distinctive Feature/Quality.svg';
import CheckIcon from '@assets/images/svg/Distinctive Feature/check.svg';
import ServiceIcon from '@assets/images/svg/Distinctive Feature/service.svg';

import { useRef } from 'react';
import { gsap } from 'gsap/gsap-core';
import { useGSAP } from '@gsap/react';
import { Observer } from 'gsap/Observer';

gsap.registerPlugin(Observer);

const DistinctiveFeature = () => {
  const distinctiveFeatureRef = useRef<HTMLDivElement>(null);

  useGSAP(
    () => {
      const tl = gsap.timeline({
        paused: true,
        defaults: { y: 150, autoAlpha: 0, duration: 3 },
      });

      tl.from(distinctiveFeatureRef.current, {});

      const observer = Observer.create({
        type: 'wheel,scroll,touch,',
        onDown: () => {
          tl.play();
          observer.kill();
        },
        onUp: () => {
          tl.play();
          observer.kill();
        },
        preventDefault: false,
      });
    },
    { scope: distinctiveFeatureRef },
  );

  return (
    <div className={styles['distinctive-feature']} ref={distinctiveFeatureRef}>
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
