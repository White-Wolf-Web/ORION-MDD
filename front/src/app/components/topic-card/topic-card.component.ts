import {
  Component,
  OnInit,
  Input,
  Output,
  EventEmitter,
  SimpleChanges,
} from '@angular/core';

@Component({
  selector: 'app-topic-card',
  templateUrl: './topic-card.component.html',
  styleUrls: ['./topic-card.component.scss'],
})
export class TopicCardComponent implements OnInit {
  @Input() cardTitle!: string;
  @Input() cardContent!: string;
  @Input() unsubscribe: boolean = false;
  

  @Output() actionClick = new EventEmitter<boolean>();     // Pour créer l'action d'abonnement ou du désabonnement

  onButtonClick() {
    console.log(
      'Button clicked, current unsubscribe status:',
      this.unsubscribe
    );
    this.unsubscribe = !this.unsubscribe;                  // Pour tester, mise à jour de l'état localement
    this.actionClick.emit(this.unsubscribe);
    console.log('After click, new unsubscribe status:', this.unsubscribe);
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['unsubscribe']) {
      console.log('Unsubscribe status changed:', this.unsubscribe);
      console.log('Previous value:', changes['unsubscribe'].previousValue);
      console.log('Current value:', changes['unsubscribe'].currentValue);
    }
  }

  constructor() {}

  ngOnInit(): void {}
}
