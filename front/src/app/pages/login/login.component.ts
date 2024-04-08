import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Login } from 'src/app/models/login.model';
import { AuthResponse } from 'src/app/models/authResponse.model';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  public hide = true;                           // Utilisé pour contrôler la visibilité du mot de passe
  public onError = false;                       // Flag pour afficher les messages d'erreur

  loginForm: FormGroup;

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router,
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
    });
  }

  ngOnInit(): void {}

  onLogin() {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe({
        next: (response: AuthResponse) => {
        
          localStorage.setItem('auth_token', response.token); // Stockez le token JWT
          this.router.navigate(['/articles']);                // Ajustez la route selon votre application
        },
        error: (error) => {
          console.error('Erreur de connexion', error);
          this.onError = true; // Activez le flag d'erreur pour afficher un message d'erreur si nécessaire
        },
      });
    }
  }
  

  get email() {
    return this.loginForm.get('email');
  }

  get password() {
    return this.loginForm.get('password');
  }
}
