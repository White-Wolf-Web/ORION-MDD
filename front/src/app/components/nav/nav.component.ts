import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss'],
})
export class NavComponent implements OnInit {
  activeLink!: string;
  @ViewChild('sidenav') sidenav!: MatSidenav;
  isSidenavOpen = false;

  constructor(private router: Router) {}

  ngOnInit(): void {}

  toggleSidenav(): void {
    this.isSidenavOpen ? this.sidenav.close() : this.sidenav.open();
  }

  sidenavOpenedChanged(isOpen: boolean): void {
    this.isSidenavOpen = isOpen;
  }

  setActiveLink(link: string): void {
    this.activeLink = link;
    this.sidenav.close();
  }

  navigateToMe(): void {
    this.router.navigate(['/me']);
  }

  navigateToArticles(): void {
    this.router.navigate(['/articles']); 
  }
  
  navigateToTopics(): void {
    this.router.navigate(['/topics']);
  }
}
