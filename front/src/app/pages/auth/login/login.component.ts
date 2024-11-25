import { Component, HostListener } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { BackArrowComponent } from '../../../components/back-arrow/back-arrow.component';
import { ButtonComponent } from 'src/app/components/button/button.component';
import { LogoComponent } from 'src/app/components/logo/logo.component';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoginResponse } from 'src/app/models/login-response.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    BackArrowComponent,
    ButtonComponent,
    LogoComponent,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  isSmallScreen = window.innerWidth < 768;  // Détecte si l'écran est petit 
  identifier: string = '';                  // Champ pour capturer l'identifiant (email ou nom).
  password: string = '';                    // Champ pour capturer le mot de passe de l'utilisateur.

  constructor(private http: HttpClient, private router: Router) {}

 // Méthode appelée lors de la soumission du formulaire de connexion.
   //Elle envoie une requête POST au serveur avec les informations d'identification de l'utilisateur.
   
  onSubmit() {
    const loginData = { identifier: this.identifier, password: this.password }; // Préparation des données de connexion à envoyer au serveur.

    this.http.post<LoginResponse>('/api/auth/login', loginData).subscribe(
      (response) => {
        console.log('Login successful:', response);
        localStorage.setItem('token', response.token);     // Stocke le jeton JWT dans le localStorage
        this.router.navigate(['/articles']);
      },
      (error: Error) => {
        console.error('Login failed:', error);
        alert(
          'Échec de la connexion. Veuillez vérifier vos informations d’identification.'
        );
      }
    );
  }

// Gestion de la redimensionnement de la fenêtre
@HostListener('window:resize', ['$event'])
onResize(event: Event): void {
  const target = event.target as Window;
  this.isSmallScreen = target.innerWidth < 768;
}
}
