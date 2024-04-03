import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-theme-card',
  templateUrl: './theme-card.component.html',
  styleUrls: ['./theme-card.component.scss']
})
export class ThemeCardComponent implements OnInit {
  @Input() cardTitle!: string;
  @Input() cardContent!: string;
  constructor() { }

  ngOnInit(): void {
  }

}
