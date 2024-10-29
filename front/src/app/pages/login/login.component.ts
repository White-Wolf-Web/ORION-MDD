import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BackArrowComponent } from '../../components/back-arrow/back-arrow.component';
import { ButtonComponent } from 'src/app/components/button/button.component'; 

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, BackArrowComponent, ButtonComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  email: string = ''; 
  password: string = ''; 
  constructor() {}

  onSubmit() {
    console.log('Email:', this.email);
    console.log('Password:', this.password);
   
  }
}
