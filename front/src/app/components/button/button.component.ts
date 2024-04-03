import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.scss']
})
export class ButtonComponent implements OnInit {
  @Input() buttonText!: string; 
  @Input() buttonType: 'primary' | 'secondary' = 'primary'; // Il est définit sur le bouton violet par défaut
  @Input() disabled: boolean = false; 
  constructor() { }

  ngOnInit(): void {
  }
}
/*
@Input() est un décorateur qui marque la propriété comme un input.
Cela signifie que la valeur de cette propriété peut être passée au composant depuis un parent.
buttonText' est le nom de l'input. Le '!' signifie que la propriété doit être obligatoirement fournie par le parent,
indiquant à TypeScript que cette propriété sera initialisée d'une manière ou d'une autre avant qu'elle ne soit utilisée.
*/