import { Component, Input } from '@angular/core';
import { ButtonComponent } from '../button/button.component';

@Component({
  selector: 'app-subcription-card',
  standalone: true,
  imports: [ButtonComponent],
  templateUrl: './subcription-card.component.html',
  styleUrls: ['./subcription-card.component.scss']
})
export class SubcriptionCardComponent {
  @Input() title!: string;
  @Input() content!: string;
}
