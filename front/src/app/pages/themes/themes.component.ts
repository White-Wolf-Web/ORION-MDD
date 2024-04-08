import { Component, OnInit } from '@angular/core';
import { Theme } from 'src/app/models/theme.model';
import { SubscriptionService } from 'src/app/services/subscription/subscription.service';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss'],
})
export class ThemesComponent implements OnInit {
  themes: Theme[] = [ ];

  constructor(
    private cdr: ChangeDetectorRef,
    private subscriptionService: SubscriptionService
  ) {}

  updateTheme(themeId: number, subscribed: boolean): void {
    const index = this.themes.findIndex((t) => t.id === themeId);
    if (index !== -1) {
      this.themes[index].subscribed = subscribed;
      this.themes = [...this.themes]; // Recréer le tableau pour forcer la détection des changements
      console.log(
        `Theme updated: ${this.themes[index].title}, subscribed: ${this.themes[index].subscribed}`
      );
    }
  }

  ngOnInit(): void {}

  handleAction(theme: Theme, action: boolean): void {
    console.log(
      'Handling action for theme:',
      theme.title,
      ', Action is subscribe:',
      !action
    );

    if (!action) {
      this.subscriptionService.subscribeToTheme(theme.id).subscribe(() => {
        console.log('Successfully subscribed.');
        this.updateTheme(theme.id, true); // Mise à jour de l'état d'abonnement à true
        this.cdr.detectChanges(); // Assurez-vous que cela est appelé après la mise à jour
      });
    } else {
      this.subscriptionService.unsubscribeFromTheme(theme.id).subscribe(() => {
        console.log('Successfully unsubscribed.');
        this.updateTheme(theme.id, false); // Mise à jour de l'état d'abonnement à false
        this.cdr.detectChanges(); // Assurez-vous que cela est appelé après la mise à jour
      });
    }
  }
}
