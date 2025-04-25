import { ROLES } from '@/api/ROLES';
import { useAppSelector } from '@/hooks/reduxHooks';
import CenterProblems from './CenterProblems/CenterProblems';
import ProfessionalProblem from './ProfessionalProblem/ProfessionalProblem';
import ClientsProblems from './ClientsProblems/ClientsProblems';

const Problems = () => {
  const { user } = useAppSelector((state) => state.auth);
  console.log(user?.role);
  if (user?.role === ROLES.CITIZEN) return <ClientsProblems />;
  if (user?.role === ROLES.SUPPORT_CENTER) return <CenterProblems />;
  if (user?.role === ROLES.PROFESSIONAL) return <ProfessionalProblem />;

  return <p>Error</p>;
};

export default Problems;
