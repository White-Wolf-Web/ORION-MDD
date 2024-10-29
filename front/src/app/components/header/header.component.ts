import { Component, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent {
  isMenuOpen = false;
  isSmallScreen = window.innerWidth < 768;

  constructor(public router: Router) {}

  // Méthode pour vérifier si l'utilisateur est sur une page d'authentification
  isAuthPage(): boolean {
    return this.router.url === '/login' || this.router.url === '/register';
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.isSmallScreen = event.target.innerWidth < 768;
  }
}
