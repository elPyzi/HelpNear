import styles from './AboutUs.module.scss';

import Talk from '@assets/images/talk.png';
import Pause from '@assets/images/pause.png';

const AboutUs = () => {
  return (
    <div className={styles['about-us']}>
      <img
        src={Talk}
        alt="Разговор важен"
        className={styles['about-us__img']}
      />

      <div className={styles['about-us__img-info']}>
        <p className={styles['about-us__img-title']}>Разговор</p>
        <p className={styles['about-us__img-text']}>
          Мы верим, что разговор — это путь к свободе и пониманию. Открытое
          общение помогает нам лучше понимать друг друга, делиться мыслями и
          чувствами, а также находить решения для сложных ситуаций. Каждый
          разговор — это возможность для роста и развития, как личного, так и
          коллективного.
        </p>
      </div>

      <img src={Pause} alt="Пауза важна" className={styles['about-us__img']} />

      <div className={styles['about-us__img-info']}>
        <p className={styles['about-us__img-title']}>Пауза</p>
        <p className={styles['about-us__img-text']}>
          Иногда все, что нужно, — это остановиться и сделать вдох-выдох. Пауза
          позволяет нам переосмыслить происходящее, восстановить силы и
          сосредоточиться на том, что действительно важно. В моменты напряжения
          пауза может стать ключом к ясности и спокойствию, помогая нам
          принимать более взвешенные решения.
        </p>
      </div>

      <div className={styles['about-us__info']}>
        <h3 className={styles['about-us__title']}>О нас</h3>
        <p className={styles['about-us__text']}>
          Мы — команда профессионалов, стремящихся помочь людям в их
          повседневной жизни. Наша цель — создать безопасное и поддерживающее
          пространство, где каждый может получить необходимую помощь и
          поддержку. Мы верим в силу общения и важность пауз, которые позволяют
          нам лучше понимать друг друга и находить решения сложных ситуаций.
          Наша работа основана на доверии, уважении и стремлении к качеству, и
          мы гордимся тем, что можем быть рядом с вами в трудные моменты. Вместе
          мы можем достичь большего!
        </p>
      </div>
    </div>
  );
};

export default AboutUs;
