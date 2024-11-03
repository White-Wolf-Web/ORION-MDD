import { Component, HostListener } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'; 
import { BackArrowComponent } from '../../components/back-arrow/back-arrow.component';
import { ButtonComponent } from 'src/app/components/button/button.component'; 
import { LogoComponent } from 'src/app/components/logo/logo.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  standalone: true,
  imports: [CommonModule, FormsModule, BackArrowComponent, ButtonComponent, LogoComponent], 
})
export class RegisterComponent {
  isSmallScreen = window.innerWidth < 768;
  username = '';
  email = '';
  password = '';

  onSubmit() {
    // Ajouter logique pour inscription
    console.log('User registered:', this.username, this.email);
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.isSmallScreen = event.target.innerWidth < 768;
  }
}
