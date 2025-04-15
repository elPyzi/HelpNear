import { Professional } from '@/types/Users';

type ProfessionalCardProps = Pick<
  Professional,
  'avatar' | 'full_name' | 'email' | 'rating' | 'contact_number'
>;

const ProfessionalCard = ({
  avatar,
  full_name,
  email,
  rating,
  contact_number,
}: ProfessionalCardProps) => {
  return <div>Professional</div>;
};

export default ProfessionalCard;
