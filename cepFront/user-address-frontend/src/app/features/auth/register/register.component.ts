import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { CommonModule } from '@angular/common';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, CommonModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  form: FormGroup;
  loading = false;
  delayTime: number = 2_000;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.form = this.fb.nonNullable.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      role: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.form.invalid) return;

    this.loading = true;

    const { name, email, password, role } = this.form.getRawValue();

    setTimeout(() => {
      this.authService.register(name, email, password, role).subscribe({
        next: () => {
          this.loading = false;
          this.onSuccessAlert();
        },
        error: (err) => {
          this.loading = false;
          this.onErrorAlert(err);
        }
      });
    }, this.delayTime);
  }

  onSuccessAlert = () => {
    Swal.fire({
      icon: 'success',
      title: 'Registration successful',
      text: 'You can now log in to your account.',
      confirmButtonColor: '#3f51b5'
    }).then(() => {
      this.router.navigate(['/login']);
    });
  }

  onErrorAlert = (err: any) => {
    Swal.fire({
      icon: 'error',
      title: 'Registration failed',
      text: 'An error occurred during registration.',
      confirmButtonColor: '#d33'
    });
  }

}