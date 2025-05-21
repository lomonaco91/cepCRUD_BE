import { Address } from "./address.model";

export interface User {
  id: number;
  name: string;
  email: string;
  role: 'ADMIN' | 'USER';
  createdAt: Date;
  updatedAt: Date;
  addresses?: Address[];
}

export interface UserForm {
  name: string;
  email: string;
  password: string;
  role?: 'ADMIN' | 'USER';
}