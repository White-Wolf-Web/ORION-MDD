import { Component, OnInit } from '@angular/core';
import { Theme } from 'src/app/models/theme.model';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss']
})
export class ThemesComponent implements OnInit {
  themes: Theme[] = [
    {
      title: 'Theme 1',
      content: 'Contenu theme 1...',
    },
    {
      title: 'Theme 2',
      content: 'Contenu du theme 2...',
    },
    
  ];

  constructor() { }

  ngOnInit(): void {
  }
}