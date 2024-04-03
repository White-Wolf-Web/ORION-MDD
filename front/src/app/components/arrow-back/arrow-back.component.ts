import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-arrow-back',
  templateUrl: './arrow-back.component.html',
  styleUrls: ['./arrow-back.component.scss'],
})
export class ArrowBackComponent implements OnInit {
  constructor(private router: Router) {}

  ngOnInit(): void {}
  goBack(): void {
    window.history.back();
  }
}
