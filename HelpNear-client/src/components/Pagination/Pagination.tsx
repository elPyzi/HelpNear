import styles from './Pagination.module.scss';

import { useState, memo } from 'react';

type PaginationProps = {
  onNextPageClick: () => void;
  onPrevPageClick: () => void;
  disable: {
    left: boolean;
    right: boolean;
  };
  nav?: {
    current: number;
    total: number;
  };
};

const Pagination = ({
  onNextPageClick,
  onPrevPageClick,
  disable,
  nav,
}: PaginationProps) => {
  const handleNextPageClick = () => {
    onNextPageClick();
  };

  const handlePrevPageClick = () => {
    onPrevPageClick();
  };

  return (
    <div className={styles['pagination']}>
      <button
        type="button"
        className={`${styles['pagination__btn']} ${styles['pagination__btn-left']}`}
        onClick={handlePrevPageClick}
        disabled={disable.left}
      >
        {'<'}
      </button>
      {nav && <span className={styles['pagination__nav']}>{nav.current}</span>}
      <button
        type="button"
        className={`${styles['pagination__btn']} ${styles['pagination__btn-right']}`}
        onClick={handleNextPageClick}
        disabled={disable.right}
      >
        {'>'}
      </button>
    </div>
  );
};

export default memo(Pagination);
