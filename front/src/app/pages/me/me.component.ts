import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SubscriptionDto } from '../../models/subscription.model';
import { SubcriptionCardComponent } from 'src/app/components/subcription-card/subcription-card.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from 'src/app/components/button/button.component'; 

@Component({
  selector: 'app-me',
  standalone: true,
  imports: [FormsModule, CommonModule, ButtonComponent, SubcriptionCardComponent],
  templateUrl: './me.component.html',
  styleUrl: './me.component.scss'
})
export class MeComponent implements OnInit {
  user: any = {}; // Initialize user object
  subscriptions: SubscriptionDto[] = []; // Array to hold user subscriptions

  constructor(private http: HttpClient) {}

  ngOnInit() {
    // Fetch user profile
    this.http.get('/api/me').subscribe({
      next: (data) => {
        this.user = data;
      },
      error: (error) => {
        console.error('Error fetching user profile:', error);
      },
    });

    // Fetch user subscriptions
    this.http.get<SubscriptionDto[]>('/api/me/subscriptions').subscribe({
      next: (data) => {
        this.subscriptions = data;
      },
      error: (error) => {
        console.error('Error fetching subscriptions:', error);
      },
    });
  }

  // Save updated profile information
  saveProfile() {
    this.http.put('/api/me', this.user).subscribe({
      next: () => alert('Profil mis à jour'),
      error: (error) => console.error('Error saving profile:', error),
    });
  }

  // Handle user logout
  logout() {
    // Implement logout logic, like clearing tokens
    console.log('User logged out');
  }
}
