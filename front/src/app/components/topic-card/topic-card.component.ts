import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-topic-card',
  templateUrl: './topic-card.component.html',
  styleUrls: ['./topic-card.component.scss'],
})
export class TopicCardComponent implements OnInit {
  @Input() cardTitle!: string;
  @Input() cardContent!: string;
  @Input() unsubscribe: boolean = true;
@Input() subscribed!: boolean  
  @Output() actionClick = new EventEmitter<boolean>(); // Pour créer l'action d'abonnement ou du désabonnement

  onButtonClick() {
    console.log(
      'Button clicked, current unsubscribe status:',
      this.unsubscribe
    );
    this.unsubscribe = !this.unsubscribe; // Pour tester, mise à jour de l'état localement
    this.actionClick.emit(this.unsubscribe);
    console.log('After click, new unsubscribe status:', this.unsubscribe);
  }

  constructor() {}

  ngOnInit(): void {}
}
