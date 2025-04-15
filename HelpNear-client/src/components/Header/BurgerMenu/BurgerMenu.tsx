import styles from './BurgerMenu.module.scss';

import { PAGE_CONFIG } from '@/config/page.config';

import { useState, useRef } from 'react';
import { gsap } from 'gsap/gsap-core';
import { useGSAP } from '@gsap/react';
import { NavLink } from 'react-router-dom';

gsap.registerPlugin(useGSAP);

const BurgerMenu = () => {
  const burgerMenuScopeRef = useRef<HTMLDivElement>(null);
  const burgerMenuRef = useRef<HTMLDivElement>(null);
  const burgerMenuLinksRef = useRef<(HTMLAnchorElement | null)[]>([]);

  const [burgerMenuTl] = useState(
    gsap.timeline({
      paused: true,
      defaults: { duration: 0.3 },
    }),
  );

  const { contextSafe: burgerMenuBtn } = useGSAP(
    () => {
      burgerMenuTl
        .to('.line1', { rotate: -45 })
        .to('.line2', { alpha: 0 }, 0)
        .to('.line3', { rotate: 45 }, 0)
        .to(
          burgerMenuRef.current,
          {
            autoAlpha: 1,
            yPercent: 100,
          },
          0,
        )
        .from(burgerMenuLinksRef.current, {
          y: -100,
          autoAlpha: 0,
          stagger: 0.3,
        })
        .reverse();
    },
    { scope: burgerMenuScopeRef },
  );

  const toggleBurgerMenu = burgerMenuBtn(() => {
    burgerMenuTl.reversed(!burgerMenuTl.reversed());
  });

  return (
    <div className={styles['burger-menu']} ref={burgerMenuScopeRef}>
      <button
        type="button"
        className={styles['burger-menu__btn']}
        onClick={toggleBurgerMenu}
      >
        <svg
          viewBox="0 0 24 24"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
          className={styles['burger-menu__svg']}
        >
          <path d="M4 18L20 18" className="line1"></path>
          <path d="M4 12L20 12" className="line2"></path>
          <path d="M4 6L20 6" className="line3"></path>
        </svg>
      </button>

      <nav className={styles['burger-menu__nav']} ref={burgerMenuRef}>
        <NavLink
          to={PAGE_CONFIG.HOME}
          className={({ isActive }) =>
            isActive
              ? `${styles['burger-menu__link']} ${styles['burger-menu__link--active']}`
              : `${styles['burger-menu__link']}`
          }
          ref={(el) => {
            if (el) burgerMenuLinksRef.current[0] = el;
          }}
        >
          Главная
        </NavLink>
        <NavLink
          to={PAGE_CONFIG.SUPPORT_CENTER}
          className={({ isActive }) =>
            isActive
              ? `${styles['burger-menu__link']} ${styles['burger-menu__link--active']}`
              : `${styles['burger-menu__link']}`
          }
          ref={(el) => {
            if (el) burgerMenuLinksRef.current[1] = el;
          }}
        >
          Центры
        </NavLink>
        <NavLink
          to={PAGE_CONFIG.PROFESSIONAL}
          className={({ isActive }) =>
            isActive
              ? `${styles['burger-menu__link']} ${styles['burger-menu__link--active']}`
              : `${styles['burger-menu__link']}`
          }
          ref={(el) => {
            if (el) burgerMenuLinksRef.current[2] = el;
          }}
        >
          Профессионалы
        </NavLink>
        <NavLink
          to={PAGE_CONFIG.ACCOUNT}
          className={({ isActive }) =>
            isActive
              ? `${styles['burger-menu__link']} ${styles['burger-menu__link--active']}`
              : `${styles['burger-menu__link']}`
          }
          ref={(el) => {
            if (el) burgerMenuLinksRef.current[3] = el;
          }}
        >
          Профиль
        </NavLink>
      </nav>
    </div>
  );
};

export default BurgerMenu;
