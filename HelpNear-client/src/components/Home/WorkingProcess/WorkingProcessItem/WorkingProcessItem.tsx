import styles from './WorkingProcessItem.module.scss';

type WorkingProcessItemProps = {
  title: string;
  description: string;
};

const WorkingProcessItem = ({
  title,
  description,
}: WorkingProcessItemProps) => {
  return (
    <div className={styles['working-process-item']}>
      <h4 className={styles['working-process-item__title']}>{title}</h4>
      <p className={styles['working-process-item__text']}>{description}</p>
    </div>
  );
};

export default WorkingProcessItem;
