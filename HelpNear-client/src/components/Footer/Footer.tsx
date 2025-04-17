import styles from './Footer.module.scss';

import HelpNear from '@assets/images/svg/HN.svg';

import TelegramIcon from '@assets/images/svg/socialMedia/tg.svg';
import InstagramIcon from '@assets/images/svg/socialMedia/inst.svg';
import TwitterIcon from '@assets/images/svg/socialMedia/twitter.svg';

import { Link } from 'react-router-dom';

const Footer = () => {
  return (
    <footer className={styles['footer']}>
      <div className="container">
        <HelpNear className={styles['footer__logo']} />
        <nav className={styles['footer__nav']}>
          <Link to="" className={styles['footer__links']}>
            О нас
          </Link>
          <Link to="" className={styles['footer__links']}>
            Блог
          </Link>
          <Link to="" className={styles['footer__links']}>
            FAQ
          </Link>
        </nav>
        <div className={styles['footer__social-media']}>
          <TelegramIcon className={styles['footer__social-media-icon']} />
          <InstagramIcon className={styles['footer__social-media-icon']} />
          <TwitterIcon className={styles['footer__social-media-icon']} />
        </div>
        <p className={styles['footer__copy']}>&copy; HelpNear.company</p>
      </div>
    </footer>
  );
};

export default Footer;
