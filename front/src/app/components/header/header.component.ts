import { Component, HostListener } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent {
  isMenuOpen = false;
  isSmallScreen = window.innerWidth < 768;
  autoCloseTimeout!: ReturnType<typeof setTimeout>;

  constructor(public router: Router) {}

  // Méthode pour vérifier si l'utilisateur est sur une page d'authentification
  isAuthPage(): boolean {
    return this.router.url === '/login' || this.router.url === '/register';
  }

  // Méthode pour basculer le menu hamburger
  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;

    if (this.isMenuOpen) {
      clearTimeout(this.autoCloseTimeout);

      this.autoCloseTimeout = setTimeout(() => {
        this.isMenuOpen = false;
      }, 5000);
    }
  }

  // Gestion de la redimensionnement de la fenêtre
  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.isSmallScreen = event.target.innerWidth < 768;
  }

  // Méthode pour vérifier si une route est active et appliquer un style
  isActive(route: string): boolean {
    return this.router.url === route;
  }
}
