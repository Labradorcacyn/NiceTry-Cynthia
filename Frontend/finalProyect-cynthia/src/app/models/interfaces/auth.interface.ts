export interface AuthLoginResponse {
  id: string;
  nick: string;
  email: string;
  name: string;
  avatar: string;
  role: string;
  token: string;
}

export interface AuthSignUpResponse {
  name: string;
  lastName: string;
  id: string;
  nick: string;
  email: string;
  city: string;
  avatar: string;
  userRoles: string;
}
