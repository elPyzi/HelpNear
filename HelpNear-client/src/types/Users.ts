import { ROLES } from '@/api/ROLES';

export type User = {
  id: number;
  login: string;
  password: string;
  fullName: string;
  email: string;
  address: string;
  contactNumber: string;
  birthDate: string;
  role: ROLES;
  avatar?: string;
  isBanned: boolean;
};

export type Professional = User & {
  rating: number;
  center: string;
  info: string;
};
