import styles from './Header.module.scss';

import { useRef } from 'react';
import { NavLink } from 'react-router-dom';
import { gsap } from 'gsap/gsap-core';
import { useGSAP } from '@gsap/react';

import ForestVideo from '@assets/images/video/forest.mp4';
import WaterVideo from '@assets/images/video/water.mp4';
import SkyVideo from '@assets/images/video/sky.mp4';

import ForestPoster from '@assets/images/forest.jpeg';
import WaterPoster from '@assets/images/water.jpeg';
import SkyPoster from '@assets/images/sky.jpeg';

import BurgerMenu from './BurgerMenu/BurgerMenu';

import HelpNear from '@assets/images/svg/HN.svg';
import Profile from '@assets/images/svg/profile.svg';

gsap.registerPlugin(useGSAP);

const Header = () => {
  const slogansScopeRef = useRef<HTMLDivElement>(null);
  const slogansRef = useRef<HTMLParagraphElement[]>([]);

  useGSAP(
    () => {
      gsap.to(slogansRef.current, {
        opacity: 1,
        duration: 0.5,
        stagger: 1,
        ease: 'power2.out',
      });
    },
    { scope: slogansScopeRef },
  );

  const video = [ForestVideo, WaterVideo, SkyVideo];
  const poster = [ForestPoster, WaterPoster, SkyPoster];

  const random = Math.floor(Math.random() * 3);

  return (
    <header className={styles['header']}>
      <div className={styles['header__preview']}>
        <video
          src={video[random]}
          poster={poster[random]}
          autoPlay
          muted
          loop
          className={styles['header__video']}
        ></video>
        <div className={styles['header__slogans']} ref={slogansScopeRef}>
          <p
            className={styles['header__slogan']}
            ref={(el) => {
              if (el) slogansRef.current[0] = el;
            }}
          >
            Помощь
          </p>
          <p
            className={styles['header__slogan']}
            ref={(el) => {
              if (el) slogansRef.current[1] = el;
            }}
          >
            Поддержка
          </p>
          <p
            className={styles['header__slogan']}
            ref={(el) => {
              if (el) slogansRef.current[2] = el;
            }}
          >
            Спасение
          </p>
        </div>
      </div>

      <nav className={`container ${styles['header__nav']}`}>
        <NavLink to="/">{/* <HelpNear /> */}#HelpHear</NavLink>
        <div className={styles['header__links']}>
          <NavLink
            to="support-centers"
            className={({ isActive }) =>
              isActive
                ? `${styles['header__link']} ${styles['header__link--active']}`
                : styles['header__link']
            }
          >
            Центры
          </NavLink>
          <NavLink
            to="/"
            className={({ isActive }) =>
              isActive
                ? `${styles['header__link']} ${styles['header__link--active']}`
                : styles['header__link']
            }
          >
            Главная
          </NavLink>
          <NavLink
            to="professionals"
            className={({ isActive }) =>
              isActive
                ? `${styles['header__link']} ${styles['header__link--active']}`
                : styles['header__link']
            }
          >
            Профессионалы
          </NavLink>
        </div>
        <NavLink
          to="account"
          className={({ isActive }) =>
            isActive
              ? `${styles['header__profile']} ${styles['header__profile--active']}`
              : styles['header__profile']
          }
        >
          <Profile />
        </NavLink>
        <BurgerMenu />
      </nav>
    </header>
  );
};

export default Header;
