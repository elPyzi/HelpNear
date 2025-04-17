export type Error = {
  title: string;
  message: string;
};

export type AuthError = Error & {
  field: string;
};
