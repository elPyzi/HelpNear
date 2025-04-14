import styles from './Overview.module.scss';

import HelpHand from '@assets/images/help hand.png';

import { useRef } from 'react';
import { gsap } from 'gsap/gsap-core';
import { useGSAP } from '@gsap/react';

const Overview = () => {
  const overviewScope = useRef<HTMLDivElement>(null);
  const titlesRef = useRef<HTMLDivElement[]>([]);
  const missionRef = useRef<HTMLDivElement>(null);
  const imgRef = useRef<HTMLImageElement>(null);

  useGSAP(
    () => {
      const overviewTl = gsap.timeline({
        // paused: true,
        defaults: { duration: 3 },
      });

      overviewTl
        .from(
          titlesRef.current,
          {
            x: -100,
            opacity: 0,
            stagger: 0.5,
          },
          0,
        )
        .from(
          imgRef.current,
          {
            x: 100,
            opacity: 0,
          },
          0,
        )
        .from(missionRef.current, { x: -100, opacity: 0 }, 1.5);
    },
    { scope: overviewScope },
  );

  return (
    <div className={styles['overview']} ref={overviewScope}>
      <div className={styles['overview__slogans']}>
        <h2
          className={styles['overview__title']}
          ref={(el) => {
            if (el) titlesRef.current[0] = el;
          }}
        >
          Профессионализм
        </h2>
        <h2
          className={styles['overview__title']}
          ref={(el) => {
            if (el) titlesRef.current[1] = el;
          }}
        >
          Услуги безопасности
        </h2>
        <h2
          className={styles['overview__title']}
          ref={(el) => {
            if (el) titlesRef.current[2] = el;
          }}
        >
          Вы можете доверять нам
        </h2>

        <p className={styles['overview__mission']} ref={missionRef}>
          Наша миссия — поддерживать тех, кто в этом нуждается. В трудные
          моменты важно иметь надежных партнеров. Наша команда предлагает
          всестороннюю помощь в вопросах безопасности и сложных ситуациях. Мы
          создаем атмосферу доверия, чтобы каждый чувствовал себя в безопасности
          и уверенности в завтрашнем дне.
        </p>
      </div>
      <div className={styles['overview__help-hand']} ref={imgRef}>
        <img
          src={HelpHand}
          alt="Помощь рукой"
          className={styles['overview__img']}
        />
      </div>
    </div>
  );
};

export default Overview;
