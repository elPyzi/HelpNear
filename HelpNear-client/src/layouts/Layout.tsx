import { Outlet } from 'react-router-dom';

import Footer from '@/components/Footer/Footer';
import Header from '@/components/Header/Header';
import Cookie from '@/components/Cookie/Cookie';

import styles from './Layout.module.scss';

const Layout = () => {
  return (
    <>
      <Header />
      <main className={styles['layout']}>
        <Outlet />
      </main>
      <Cookie />

      <Footer />
    </>
  );
};

export default Layout;
