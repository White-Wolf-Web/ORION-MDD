import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import { UserService } from 'src/app/services/user/user.service';
import { SubscriptionService } from 'src/app/services/subscription/subscription.service';
import { Subscription } from 'src/app/models/subscription.model';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss'],
})
export class MeComponent implements OnInit {
  userForm = new FormGroup({
    username: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
  });
  subscriptions: Subscription[] = [];

  constructor(
    private router: Router,
    private authService: AuthService,
    private userService: UserService,
    private subscriptionService: SubscriptionService
  ) {}

  ngOnInit(): void {
    this.loadUserData();
    this.loadSubscriptions();
  }

  loadSubscriptions(): void {
    this.subscriptionService.getSubscriptionsForUser().subscribe({
      next: (subscriptions: Subscription[]) => {
        this.subscriptions = subscriptions;
      },
      error: (error: any) =>
        console.error('Erreur lors du chargement des abonnements', error),
    });
  }

  loadUserData(): void {
    this.userService.getCurrentUser().subscribe({
      next: (user: { username: string; email: string }) => {
        this.userForm.patchValue({
          username: user.username,
          email: user.email,
        });
      },
      error: (error: any) =>
        console.error(
          'Erreur lors du chargement des données utilisateur',
          error
        ),
    });
  }

  get username(): FormControl {
    return this.userForm.get('username') as FormControl;
  }

  get email(): FormControl {
    return this.userForm.get('email') as FormControl;
  }

  save(): void {
    if (this.userForm.valid) {
      this.userService
        .updateUser({
          username: this.userForm.value.username!,
          email: this.userForm.value.email!,
        })
        .subscribe({
          next: (response) => {
            console.log('Mise à jour réussie', response);
          },
          error: (error) => {
            console.error('Erreur lors de la mise à jour', error);
          },
        });
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
