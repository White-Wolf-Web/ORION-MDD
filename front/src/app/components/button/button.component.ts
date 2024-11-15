import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.scss'],
  standalone: true,               // Indique que ce composant est autonome et peut être utilisé sans dépendre d'un module.
                                  // Cela simplifie l'importation dans d'autres composants ou modules.
})
export class ButtonComponent {
  @Input() text: string = '';     // Déclare une propriété d'entrée (Input) appelée `text`. 
}                                 // Cette propriété peut être définie depuis un autre composant parent qui utilise ce bouton.



