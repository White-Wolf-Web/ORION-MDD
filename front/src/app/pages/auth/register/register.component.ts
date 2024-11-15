import { Component, HostListener } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { BackArrowComponent } from 'src/app/components/back-arrow/back-arrow.component'; 
import { ButtonComponent } from 'src/app/components/button/button.component';
import { LogoComponent } from 'src/app/components/logo/logo.component';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

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

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit() {
    const registrationData = { username: this.username, email: this.email, password: this.password };
    this.http.post('/api/auth/register', registrationData).subscribe(
      (response) => {
        console.log('User registered successfully:', response);
        alert('Registration successful! You can now log in.');
        this.router.navigate(['/login']); 
      },
      (error) => {
        console.error('Registration failed:', error);
        alert('Registration failed. Please try again.');
      }
    );
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.isSmallScreen = event.target.innerWidth < 768;
  }
}
