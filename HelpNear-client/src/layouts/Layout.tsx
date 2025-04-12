import { Outlet } from 'react-router-dom';

import Footer from '@/components/Footer/Footer';
import Header from '@/components/Header/Header';
import Cookie from '@/components/Cookie/Cookie';

const Layout = () => {
  return (
    <>
      <Header />
      <main>
        <Outlet />
      </main>
      <Cookie />

      <Footer />
    </>
  );
};

export default Layout;
