import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ThemeCardComponent } from './topic-card.component';

describe('ThemeCardComponent', () => {
  let component: ThemeCardComponent;
  let fixture: ComponentFixture<ThemeCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ThemeCardComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ThemeCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
