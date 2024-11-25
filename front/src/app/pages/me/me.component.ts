import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SubscriptionDto } from '../../models/subscription.model';
import { UserDto } from 'src/app/models/user.model';
import { SubcriptionCardComponent } from 'src/app/components/cards/subcription-card/subcription-card.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from 'src/app/components/button/button.component';
import { UnsubscriptionCardComponent } from 'src/app/components/cards/unsubscription-card/unsubscription-card.component'; 
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

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
export class MeComponent implements OnInit, OnDestroy {

    // Un objet utilisateur initialisé par défaut, avec une structure conforme à UserDto
  user: UserDto = {
    id: 0,
    username: '',
    email: '',
    createdAt:'',
    subscriptions: [],
  }; 
  subscriptions: SubscriptionDto[] = [];              // Liste des abonnements de l'utilisateur.
  showPassword: boolean = false;                      // Indique si le mot de passe est visible ou non.
  private subscriptionsList = new Subscription();     // Gestion des abonnements

  constructor(private http: HttpClient, private router: Router) {}

  // Hook appelé lors de l'initialisation du composant.
  ngOnInit() {   
    this.getUserData();      // Charge les données de l'utilisateur.
  }

  // Charge les informations de l'utilisateur et ses abonnements.
  getUserData() {
    const token = localStorage.getItem('token');

    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);         // Crée des en-têtes avec le token 

      const userSub = this.http.get<UserDto>('/api/users/me', { headers }).subscribe({   // Récupérer le profil utilisateur
        next: (data: UserDto) => {
          this.user = data;                                                              // Met à jour les informations utilisateur.
        },
        error: (error) => {
          console.error(
            'Erreur lors de la récupération du profil utilisateur:',
            error
          );
        },
      });
      

      // Récupérer les abonnements de l'utilisateur
      const subsSub = this.http
        .get<SubscriptionDto[]>('/api/users/me/subscriptions', { headers })
        .subscribe({
          next: (data) => {
            this.subscriptions = data;                                         // Met à jour la liste des abonnements.
          },
          error: (error) => {
            console.error(
              'Erreur lors de la récupération des abonnements:',
              error
            );
          },
        });

       // Ajoute les abonnements aux observables pour pouvoir les gérer plus tard.
      this.subscriptionsList.add(userSub);
      this.subscriptionsList.add(subsSub);
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

  // Sauvegarde les informations du profil après un update
  saveProfile() {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    const updateSub = this.http.put('/api/users/me', this.user, { headers }).subscribe({
      next: () => {
        alert(
          'Profil mis à jour, vous allez être redirigé vers la page d’accueil pour vous reconnecter'
        );
        localStorage.removeItem('token');
        this.router.navigate(['/login']);
      },
      error: (error) =>
        console.error('Erreur lors de la mise à jour du profil:', error),
    });

    this.subscriptionsList.add(updateSub);      // Ajouter à la liste des abonnements
  }

  // Gère le désabonnement
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

  // Hook appelé lorsque le composant est détruit.
  ngOnDestroy() {
    this.subscriptionsList.unsubscribe();       // Annuler tous les abonnements pour éviter les fuites de mémoires
    console.log('Tous les abonnements dans MeComponent ont été annulés.');
  }
}
