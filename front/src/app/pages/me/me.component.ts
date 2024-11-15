import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SubscriptionDto } from '../../models/subscription.model';
import { SubcriptionCardComponent } from 'src/app/components/cards/subcription-card/subcription-card.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from 'src/app/components/button/button.component';
import { UnsubscriptionCardComponent } from 'src/app/components/cards/unsubscription-card/unsubscription-card.component'; 
import { Router } from '@angular/router';

@Component({
  selector: 'app-me',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    ButtonComponent,
    SubcriptionCardComponent,
    UnsubscriptionCardComponent,
  ],
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss'],
})
export class MeComponent implements OnInit {
  user: any = {};
  subscriptions: SubscriptionDto[] = [];
  showPassword: boolean = false;

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {
    this.getUserData();
  }

  getUserData() {
    const token = localStorage.getItem('token');

    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

      // Récupérer le profil utilisateur
      this.http.get('/api/users/me', { headers }).subscribe({
        next: (data) => {
          this.user = data;
        },
        error: (error) => {
          console.error(
            'Erreur lors de la récupération du profil utilisateur:',
            error
          );
        },
      });

      // Récupérer les abonnements de l'utilisateur
      this.http
        .get<SubscriptionDto[]>('/api/users/me/subscriptions', { headers })
        .subscribe({
          next: (data) => {
            this.subscriptions = data;
          },
          error: (error) => {
            console.error(
              'Erreur lors de la récupération des abonnements:',
              error
            );
          },
        });
    } else {
      if (window.confirm('Token non disponible. Veuillez vous connecter.')) {
        this.router.navigate(['/login']);
      }
    }
  }

  // Bascule l'affichage du mot de passe
  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  // Sauvegarde les informations du profil àpres un update
  saveProfile() {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    this.http.put('/api/users/me', this.user, { headers }).subscribe({
      next: () => {
        alert(
          'Profil mis à jour, vous allez être redirigé vers la page d acceuil pour vous reconnecter'
        );
        localStorage.removeItem('token');
        this.router.navigate(['/login']);
      },
      error: (error) =>
        console.error('Erreur lors de la mise à jour du profil:', error),
    });
  }

  // Gére le désabonnement
  onUnsubscribed(subscriptionId: number) {
    this.subscriptions = this.subscriptions.filter(
      (sub) => sub.id !== subscriptionId
    );
  }

  // Déconnexion de l'utilisateur
  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/']);
    console.log('Utilisateur déconnecté');
  }
}
