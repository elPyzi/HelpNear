import Overview from '@/components/Home/Overview/Overview';
import DistinctiveFeature from '@/components/Home/DistinctiveFeature/DistinctiveFeature';
import AboutUs from '@/components/Home/AboutUs/AboutUs';
import WorkingProcess from '@/components/Home/WorkingProcess/WorkingProcess';
import TopProfessionals from '@/components/Home/TopProfessionals/TopProfessionals';

const Home = () => {
  return (
    <div className="container">
      <Overview />
      <DistinctiveFeature />
      <AboutUs />
      <WorkingProcess />
      <TopProfessionals />
    </div>
  );
};

export default Home;
