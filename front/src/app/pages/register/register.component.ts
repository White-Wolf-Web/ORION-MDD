import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  registerForm = new FormGroup({
    username: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(6),
    ]),
  });

  constructor(private router: Router, private authService: AuthService) {}

  ngOnInit(): void {}
  onSubmit() {
    if (this.registerForm.valid) {
      this.authService
        .register({
          name: this.registerForm.value.username!,
          email: this.registerForm.value.email!,
          password: this.registerForm.value.password!,
        })
        .subscribe({
          next: () => {
            this.router.navigate(['/login']);
          },
          error: (error) => {
            console.error("Erreur d'inscription", error);
          },
        });
    }
  }

  get username() {
    return this.registerForm.get('username');
  }

  get email() {
    return this.registerForm.get('email');
  }

  get password() {
    return this.registerForm.get('password');
  }
}
