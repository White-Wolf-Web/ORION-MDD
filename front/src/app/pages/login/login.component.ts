import { Component, HostListener } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { BackArrowComponent } from '../../components/back-arrow/back-arrow.component';
import { ButtonComponent } from 'src/app/components/button/button.component';
import { LogoComponent } from 'src/app/components/logo/logo.component';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, BackArrowComponent, ButtonComponent, LogoComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss', 
})
export class LoginComponent {
  isSmallScreen = window.innerWidth < 768;
  email: string = '';
  password: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit() {
    const loginData = { email: this.email, password: this.password };
    this.http.post('/api/auth/login', loginData).subscribe(
      (response: any) => {
        console.log('Login successful:', response);
        localStorage.setItem('token', response.token); // Stocker le token JWT
        this.router.navigate(['/articles']); 
      },
      (error) => {
        console.error('Login failed:', error);
        alert('Login failed. Please check your credentials and try again.');
      }
    );
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.isSmallScreen = event.target.innerWidth < 768;
  }
}
