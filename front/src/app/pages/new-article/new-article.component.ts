import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-new-article',
  templateUrl: './new-article.component.html',
  styleUrls: ['./new-article.component.scss']
})
export class NewArticleComponent implements OnInit {
  articleForm: FormGroup;

  constructor() { 
    this.articleForm = new FormGroup({
      theme: new FormControl('', [Validators.required]), 
      title: new FormControl('', [Validators.required]),
      content: new FormControl('', [Validators.required]),
    });
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    if (this.articleForm.valid) {
      console.log(this.articleForm.value);
    }
  }
}
