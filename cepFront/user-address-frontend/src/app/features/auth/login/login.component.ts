import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { CommonModule } from '@angular/common';

import Swal from 'sweetalert2';
import { UserService } from '../../../core/services/user.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterLink,
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  form: FormGroup;
  loading = false;
  delayTime: number = 2_000;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private userService: UserService
  ) {
    this.form = this.fb.nonNullable.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  onSubmit(): void {
    if (this.form.invalid) return;

    const { email, password } = this.form.getRawValue();

    this.loading = true;

    setTimeout(() => {
      this.authService.login(email, password).subscribe({
        next: () => {
          this.loading = false;
          this.getUserIdToDelete(email);
          this.router.navigate(['/addresses/user-list']);
        },
        error: () => {
          this.loading = false;
          this.onErrorAlert();
        }
      });
    }, this.delayTime);
  }

  onErrorAlert = () => {
    Swal.fire({
      icon: 'error',
      title: 'Login Error',
      text: 'Error on login. Try again.',
      confirmButtonColor: '#d33'
    });
  }

  getUserIdToDelete = (email: string) => {
    this.userService.getUserIdToDelete(email).subscribe({
      next: (response) => {
        localStorage.setItem('user_id', response.id.toString());
      },
      error: (err) => {
        console.error('Error load user data', err);
      }
    })
  }
}