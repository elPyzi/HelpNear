import styles from './WorkingProcess.module.scss';

import Arrow from '@assets/images/svg/arrow.svg';

import WorkingProcessItem from './WorkingProcessItem/WorkingProcessItem';

const WorkingProcess = () => {
  return (
    <div className={styles['working-process']}>
      <h2 className={styles['working-process__title']}>Наш рабочий процесс</h2>
      <p className={styles['working-process__text']}>
        Мы следуем четкому и прозрачному процессу, который включает анализ
        потребностей клиента, разработку индивидуальных решений и постоянную
        поддержку на каждом этапе.
      </p>
      <div className={styles['working-process__flow']}>
        <WorkingProcessItem
          title="Разговор"
          description="В общении мы понимаем вашу проблему"
        />
        <Arrow className={styles['working-process__arrow']} />
        <WorkingProcessItem
          title="Специалисты"
          description="Наши специалисты находят под вас подход"
        />
        <Arrow className={styles['working-process__arrow']} />
        <WorkingProcessItem
          title="Решение"
          description="Решение которое удовлетворяет всех"
        />
      </div>
    </div>
  );
};

export default WorkingProcess;
