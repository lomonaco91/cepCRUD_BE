import { Routes } from '@angular/router';
import { AuthGuard } from '../app/core/auth/auth.guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    loadComponent: () => import('./features/auth/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'register',
    loadComponent: () => import('./features/auth/register/register.component').then(m => m.RegisterComponent)
  },
  {

    path: 'addresses',
    children: [
      {
        path: 'user-list',
        loadComponent: () => import('./features/addresses/user-list/user-list.component').then(m => m.UserListComponent),
        canActivate: [AuthGuard]
      },
    ]
  },
];