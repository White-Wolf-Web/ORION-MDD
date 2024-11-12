import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SubscriptionDto } from '../../models/subscription.model';
import { SubcriptionCardComponent } from 'src/app/components/subcription-card/subcription-card.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from 'src/app/components/button/button.component';
import { UnsubscriptionCardComponent } from 'src/app/components/unsubscription-card/unsubscription-card.component';

@Component({
  selector: 'app-me',
  standalone: true,
  imports: [FormsModule, CommonModule, ButtonComponent, SubcriptionCardComponent, UnsubscriptionCardComponent],
  templateUrl: './me.component.html',
  styleUrl: './me.component.scss'
})
export class MeComponent implements OnInit {
  user: any = {}; 
  subscriptions: SubscriptionDto[] = []; // Array to hold user subscriptions

  constructor(private http: HttpClient) {}

  ngOnInit() {
    const token = localStorage.getItem('token'); 
    if (!token) {
      console.error('Aucun token JWT trouvé.');
      return;
    }
    console.log('Token récupéré:', token);

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    // Récupérer le profil utilisateur
    this.http.get('/api/users/me', { headers }).subscribe({
      next: (data) => {
        this.user = data;
      },
      error: (error) => {
        console.error('Erreur lors de la récupération du profil utilisateur:', error);
      },
    });

   // Récupérer les abonnements de l'utilisateur
   this.http.get<SubscriptionDto[]>('/api/users/me/subscriptions', { headers }).subscribe({
    next: (data) => {
      this.subscriptions = data;
    },
    error: (error) => {
      console.error('Erreur lors de la récupération des abonnements:', error);
    },
  });
}

  // Sauvegarder les informations du profil
  saveProfile() {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    this.http.put('/api/users/me', this.user, { headers }).subscribe({
      next: () => alert('Profil mis à jour'),
      error: (error) => console.error('Erreur lors de la mise à jour du profil:', error),
    });
  }

// Gérer le désabonnement
onUnsubscribed(subscriptionId: number) {
  this.subscriptions = this.subscriptions.filter(sub => sub.id !== subscriptionId);
}
  
  // Déconnexion de l'utilisateur
  logout() {
    localStorage.removeItem('token'); // Supprime le token du stockage local
    console.log('Utilisateur déconnecté');
  }
}
