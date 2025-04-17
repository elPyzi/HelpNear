import Overview from '@/components/Home/Overview/Overview';
import DistinctiveFeature from '@/components/Home/DistinctiveFeature/DistinctiveFeature';
import AboutUs from '@/components/Home/AboutUs/AboutUs';
import WorkingProcess from '@/components/Home/WorkingProcess/WorkingProcess';

const Home = () => {
  return (
    <div className="container">
      <Overview />
      <DistinctiveFeature />
      <AboutUs />
      <WorkingProcess />
    </div>
  );
};

export default Home;
