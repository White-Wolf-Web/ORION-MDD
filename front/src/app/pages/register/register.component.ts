import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BackArrowComponent } from '../../components/back-arrow/back-arrow.component';
import { ButtonComponent } from 'src/app/components/button/button.component'; 

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  standalone: true,
  imports: [FormsModule, BackArrowComponent, ButtonComponent],
})
export class RegisterComponent {
  username = '';
  email = '';
  password = '';

  onSubmit() {
    // Ajouter logique pour inscription
    console.log('User registered:', this.username, this.email);
  }
}
