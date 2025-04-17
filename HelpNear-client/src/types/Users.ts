import { ROLES } from '@/api/ROLES';

export type User = {
  id: number;
  login: string;
  password: string;
  full_name: string;
  email: string;
  address: string;
  contact_number: string;
  role: ROLES;
  avatar?: string;
};

export type Professional = User & {
  rating: number;
  center: string;
  info: string;
};
