import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SubscriptionService } from 'src/app/services/subscription/subscription.service';
import { Subscription } from 'src/app/models/subscription.model';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss'],
})
export class MeComponent implements OnInit {
  userForm: FormGroup;
  subscriptions: Subscription[] = [];

  constructor(
    private router: Router,
    private authService: AuthService,
    private subscriptionService: SubscriptionService
  ) {
    this.userForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
    });
  }

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
    this.authService.getCurrentUser().subscribe({
      next: (user: User) => {
        this.userForm.patchValue({
          username: user.name,
          email: user.email,
        });
      },
      error: (error) => console.error('Error loading user data', error),
    });
  }

  get usernameControl(): FormControl {
    return this.userForm.get('username') as FormControl;
  }
  
  get emailControl(): FormControl {
    return this.userForm.get('email') as FormControl;
  }
  

  save(): void {
    if (this.userForm.valid) {
      const userData: Partial<User> = {
        name: this.userForm.value.username, // Assurez-vous que ceci correspond à la clé attendue par le backend
        email: this.userForm.value.email,
      };
      
      this.authService.updateUser(userData).subscribe({
        next: () => console.log('User updated successfully'),
        error: (error) => console.error('Error updating user', error),
      });
    }
  }
  

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
