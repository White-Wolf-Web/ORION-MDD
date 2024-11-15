import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubcriptionCardComponent } from './subcription-card.component';

describe('SubcriptionCardComponent', () => {
  let component: SubcriptionCardComponent;
  let fixture: ComponentFixture<SubcriptionCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubcriptionCardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SubcriptionCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
