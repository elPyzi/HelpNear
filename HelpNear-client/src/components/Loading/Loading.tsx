import styles from './Loading.module.scss';

import { useRef } from 'react';
import { gsap } from 'gsap/gsap-core';
import { useGSAP } from '@gsap/react';

const Loading = () => {
  const loadingScopeRef = useRef<HTMLDivElement>(null);
  const dotLoadRef = useRef<HTMLDivElement[]>([]);

  useGSAP(
    () => {
      const loadTl = gsap.timeline({
        defaults: {
          duration: 0.7,
          stagger: 0.4,
          yoyo: true,
          repeat: -1,
          ease: 'power1.out',
          repeatDelay: 0.3,
        },
      });
      loadTl.fromTo(dotLoadRef.current, { y: -10 }, { y: 10 });
    },
    { scope: loadingScopeRef },
  );

  return (
    <div className={styles['loading']} ref={loadingScopeRef}>
      <div
        className={styles['loading__dot']}
        ref={(el) => {
          if (el) dotLoadRef.current[0] = el;
        }}
      ></div>
      <div
        className={styles['loading__dot']}
        ref={(el) => {
          if (el) dotLoadRef.current[1] = el;
        }}
      ></div>
      <div
        className={styles['loading__dot']}
        ref={(el) => {
          if (el) dotLoadRef.current[2] = el;
        }}
      ></div>
    </div>
  );
};

export default Loading;
